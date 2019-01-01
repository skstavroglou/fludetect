var FluUkChoropleth = {
  init: function(div) {
    this.div = div;
    this.map = L.map(this.div).setView([54.0, -1.5], 6);
    
   // L.tileLayer('http://{s}.tile.cloudmade.com/{key}/22677/256/{z}/{x}/{y}.png', {
   //   attribution: 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; 2012 CloudMade',
   //   key: 'BC9A493B41014CAABB98F0471D759707'
   // }).addTo(this.map);

var osm = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	  }).addTo(this.map);
      
      
    mergedFeatureLayer(this.map, "/static/data/flu_occ_per_code.csv",
                                 "/static/data/topo_lad.json",
                                 "LAD13CD",
                                 this.style,
                                 null,
                                 null,
                                 "lad"
                      );
    
    addLegend([0,
               10,
               20,
               30,
               40,
               50,
               60,
               70,
               80,
               90,
               100], this.map, this.color);
    
    addInfo(this.map);

  },
  
  destroy: function() {
    this.map.remove();
  },
  
  refresh: function() {
    this.destroy();
    this.init(this.div);
  },
  
  color: function(d) {
      
    return  d == 'NA' ? 'grey' :
            d == 'undefined' ? '#333333' :
            d >= 90 ? '#980319' :
            d >= 80 ? '#c3001d' :
            d >= 70 ? '#E43404' :
            d >= 60 ? '#E48608' :
            d >= 50 ? '#E4D60C' :
            d >= 40 ? '#A4E310' :
            d >= 30 ? '#5AE314' :
            d >= 20 ? '#18E31D' :
            d >= 10 ? '#1CE268' :
            d >= 0  ? '#abe3e3' :
            'grey';
      
  },    
  style: function(feature) {
      
    return {
      fillColor: FluUkChoropleth.color(parseFloat(feature.properties.RATE)),
      weight: 1,
      opacity: 1,
      color: 'rgba(255,255,255,0.7)',
      fillOpacity: 0.8
    }
  },
    
  setDefaultView: function() {

      FluUkChoropleth.map.setView([53.0, -1.5], 5);
      
  },
  showAllProps : function() {
      
      mergedLayer.eachLayer(function (layer) {
      
          console.log(layer.feature.properties.LAD13CD + " - " +
                      layer.feature.properties.LAD13NM + " -  OCC : " +
                      layer.feature.properties.FLUOCC);
          
      });
      
  }, 
  getAllProps : function() {
      
      var res = "";
      
      mergedLayer.eachLayer(function (layer) {
          
          res += layer.feature.properties.LAD13CD + "\t" +
                 layer.feature.properties.LAD13NM + "\t" +
                 layer.feature.properties.FLUOCC + "\t" +
                 layer.feature.properties.TOTALOCC + "\n";
          
      });
      
      return res;
  }, 
  setCustomView: function(v,z) {
      
      FluUkChoropleth.map.setView(v, z);
        
  }, 
  updateValues : function(feature, fluOcc, allOcc) {
      
      mergedLayer.eachLayer(function (layer) {
          
          if (layer.feature.properties.LAD13CD == feature) {

                layer.feature.properties.FLUOCC = parseInt(fluOcc);
                layer.feature.properties.TOTALOCC = parseInt(allOcc);
              
                console.log(" [ BEFORE ] FLUOCC : " + layer.feature.properties.FLUOCC +
                         " TOTALOCC : " + layer.feature.properties.TOTALOCC +
                            " RATE : " + layer.feature.properties.RATE +
                            " LAD13CD : " + layer.feature.properties.LAD13CD +
                            "-----------------------------------");
              
                if (parseInt(allOcc) != 0 ) { 
                    layer.feature.properties.RATE = ( parseInt(fluOcc) / parseInt(allOcc) );
                }
                else {
                    layer.feature.properties.RATE = 0.0;
                }
              
                console.log(" [ AFTER ] FLUOCC : " + layer.feature.properties.FLUOCC +
                            " TOTALOCC : " + layer.feature.properties.TOTALOCC +
                            " RATE : " + layer.feature.properties.RATE +
                            " LAD13CD : " + layer.feature.properties.LAD13CD +
                            "-----------------------------------");
              
          //    [ BEFORE ] FLUOCC : NaN TOTALOCC : NaN RATE : NaN LAD13CD : E08000018-----------------------------------
          //    [ AFTER ]  FLUOCC : 0   TOTALOCC : 2   RATE : 0   LAD13CD : E08000018------------------------
              
                layer.setStyle( { fillColor : FluUkChoropleth.color(layer.feature.properties.RATE) } );
          }
          
      });
      
  },
  customStyleOntheFly: function(feature,color) {
      
    mergedLayer.eachLayer(function (layer) {
        
        if (layer.feature.properties.LAD13CD == feature) {
            
            console.log("THIS : " + layer.feature.properties.LAD13CDO);
            console.log("THIS : " + layer.feature.properties.FLUOCC);
            
            layer.feature.properties.FLUOCC = parseInt(layer.feature.properties.FLUOCC) + 1;

            console.log("THIS : " + layer.feature.properties.FLUOCC);
            
            layer.setStyle( { fillColor : color } );
            
        }
    });

  },
  
  defaultStyle: function(feature) {
    return {
      outlineColor: "#000000",
      outlineWidth: 0.5,
      weight: 1,
      opacity: 1,
      fillOpacity: 0
    };
  }
}
