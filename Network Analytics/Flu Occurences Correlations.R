#############################
# TIME SERIES CONDITIONNING #
#       VECTORISED          #
#############################

library(snow)
library(snowfall)
sfInit( parallel=TRUE, cpus=4, 
        useRscript=TRUE)  ## cpus = depends on your pc 
library(date)
library(chron)
library(timeDate)
library(timeSeries)

#############################

#### read data ####

a<-read.table("valuesTimeSlices.csv",header=TRUE,sep=",")

d.o.d.<-as.numeric(dim(a)[1]) # experiment-specfic time-steps

no.of.ts <- 380 # number of Unitary Authorities

### CUSTOM FUNCTIONS TO BE USED ###

t.s.conversion <- function (date.data,stock.data,mode.of.enum,col.ID) {
  if (mode.of.enum == 0 ) {charvec <- (date.data[,1])} 
  if (mode.of.enum == 1) {charvec <- (date.data[-d.o.d.,1])}
  charvec <- timeDate(charvec, format = "%Y-%m-%d", FinCenter = "GMT" )
  if (col.ID > 0) {data <- matrix(stock.data[,col.ID])}
  else {data <- matrix(stock.data)}
  TS <- timeSeries(data, charvec)
  return(TS)
}

## CONVERT DATA INTO TIME SERIES ##

tsALL<-as.ts(matrix(data = 0, nrow=d.o.d., ncol=no.of.ts, byrow = FALSE, dimnames = NULL))

for (i in 1:no.of.ts) {
  tsALL[,i] <- t.s.conversion(a,a,0,i+1)
}


##############################
# DIRECTED CROSS CORRELATION #
#        VECTORISED          #
##############################

# function of directed cross correlation

directed.cross.correlation <- function(ts1,ts2,time.concatenated) {
  if (time.concatenated == FALSE) {Dt<-d.o.d.}
  if (time.concatenated == TRUE) {Dt<-d.o.d.-1}
  DCC<-matrix(data = 0, nrow=Dt-1, ncol=1, byrow = FALSE, dimnames = NULL)
  for (tau in 1:Dt-1) {
    DCC[tau]<-(mean(ts1[1:(Dt-tau)]*ts2[(1+tau):Dt])-
                 mean(ts1[1:(Dt-tau)])*mean(ts2[(1+tau):Dt]))/
      (sd(ts1[1:Dt])*sd(ts2[1:Dt]))
  }
  return(DCC)
}

#### DATA MANIPULATION ####

ADJA <- array(0, dim=c(no.of.ts,no.of.ts,d.o.d.-1))

for (j in 1:no.of.ts) {
  for (k in 1:no.of.ts) {
    if (j != k) {
      ADJA[j,k,]<-directed.cross.correlation(tsALL[,j],tsALL[,k],FALSE)
    }
  }
}

ADJA[is.na(ADJA)] <- 0

#### NETWORK ANALYTICS ####

library(igraph)

# CREATE the adjacency matrix of the graph

A.M.<-graph.adjacency(ADJA[,,30], mode = "directed", weighted = TRUE, diag = FALSE,
                             add.colnames = NULL, add.rownames = NA)


# USE THRESHOLD TO DERIVE SIGNIFICANT LINKS ONLY #


A0.1<-matrix(data = 0, nrow=no.of.ts, ncol=no.of.ts, byrow = FALSE, dimnames = NULL)
  for (j in 1:no.of.ts) {
    for (k in 1:no.of.ts) {
      if (j != k) {
        if (abs(ADJA[j,k,30]) < 1) {
          A0.1[j,k] <-0
        } else {
          A0.1[j,k] <-1
        }  
      }
    }
}


A.M.0.1<-graph.adjacency(A0.1, mode = "directed", weighted = TRUE, diag = FALSE,
                                add.colnames = NULL, add.rownames = NA)


######## GET EDGELIST

h1<-get.edgelist(A.M.0.1)
write.csv(h1, file = "flulinks.csv")


###### PLOT THE GRAPH #####


### NODES ###

# load vertex attributes  ###   with numbers and names
vattr<-read.table("U.A.names.txt", header=TRUE)
vattributes<-data.frame(name1=vattr[,1],name2=vattr[,2])

### LINKS ###

# load edge attributes  #####  

eattr<-read.table("flulinks.txt", header=TRUE)
eattributes<-data.frame(node1= eattr[,1], node2= eattr[,2])

g<-(graph.data.frame)(eattributes, directed= TRUE, vertices= vattributes)

# give the names to the vector vlab

vlab<-V(g)$name2

# PLOT
g<-set.vertex.attribute( g, "label.name", 
                         value= vlab)
V(g)$degree <- degree(g, mode = "out")
plot.igraph(g, layout=layout.fruchterman.reingold, 
            asp=0.4, margin=0.0001,
            edge.color="red", edge.width=0.05, 
            vertex.shape="circle",
            vertex.frame.color="black",
            vertex.color="gold",  
            vertex.size= 2,  vertex.label= vlab, vertex.label.cex=0.5)



