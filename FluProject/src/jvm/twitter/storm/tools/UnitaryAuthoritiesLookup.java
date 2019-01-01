package twitter.storm.tools;

import java.io.Serializable;
import java.util.HashMap;

import geocode.ReverseGeoCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class that initializes a HashMap  
 * of UK unitary authority names as keys and 
 * their codes as values
 */
public class UnitaryAuthoritiesLookup implements Serializable {

	private static final long serialVersionUID = -9102878650001058090L;	
	
	// UK Counties data
	private HashMap<String, String> data;
	
	// file to read the full data
	String csvFile = "geoinfo_uk.csv";
	
	FileInputStream fis;
	ReverseGeoCode rgc; 
	
	// given in input a county name return the id code 
	public int getGeoID(String county){
		
		if (data.containsKey(county.toUpperCase())) {
			return Integer.parseInt(data.get(county.toUpperCase()));
		}
		
		return 0;
	}
	
	// given in input the coordinates of a point return the appropriate unitary authority code
	public String getUACodeByGeo(double latitude, double longitude){
		
		return rgc.nearestPlace(latitude, longitude).geoid;
	}
	
	public UnitaryAuthoritiesLookup() {
		
		try {
			
			fis = new FileInputStream(csvFile);
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		try {
			
			rgc = new ReverseGeoCode(fis, false);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		data = new HashMap<String, String>();
		
		// UNITED KINGDOM UNITARY AUTHORITIES

		data.put("Hartlepool".toUpperCase(),"cE06000001");
		data.put("Middlesbrough".toUpperCase(),"cE06000002");
		data.put("Redcar and Cleveland".toUpperCase(),"cE06000003");
		data.put("Stockton-on-Tees".toUpperCase(),"cE06000004");
		data.put("Darlington".toUpperCase(),"cE06000005");
		data.put("Halton".toUpperCase(),"cE06000006");
		data.put("Warrington".toUpperCase(),"cE06000007");
		data.put("Blackburn with Darwen".toUpperCase(),"cE06000008");
		data.put("Blackpool".toUpperCase(),"cE06000009");
		data.put("Kingston upon Hull, City of".toUpperCase(),"cE06000010");
		data.put("East Riding of Yorkshire".toUpperCase(),"cE06000011");
		data.put("North East Lincolnshire".toUpperCase(),"cE06000012");
		data.put("North Lincolnshire".toUpperCase(),"cE06000013");
		data.put("York".toUpperCase(),"cE06000014");
		data.put("Derby".toUpperCase(),"cE06000015");
		data.put("Leicester".toUpperCase(),"cE06000016");
		data.put("Rutland".toUpperCase(),"cE06000017");
		data.put("Nottingham".toUpperCase(),"cE06000018");
		data.put("Herefordshire, County of".toUpperCase(),"cE06000019");
		data.put("Telford and Wrekin".toUpperCase(),"cE06000020");
		data.put("Stoke-on-Trent".toUpperCase(),"cE06000021");
		data.put("Bath and North East Somerset".toUpperCase(),"cE06000022");
		data.put("Bristol, City of".toUpperCase(),"cE06000023");
		data.put("North Somerset".toUpperCase(),"cE06000024");
		data.put("South Gloucestershire".toUpperCase(),"cE06000025");
		data.put("Plymouth".toUpperCase(),"cE06000026");
		data.put("Torbay".toUpperCase(),"cE06000027");
		data.put("Bournemouth".toUpperCase(),"cE06000028");
		data.put("Poole".toUpperCase(),"cE06000029");
		data.put("Swindon".toUpperCase(),"cE06000030");
		data.put("Peterborough".toUpperCase(),"cE06000031");
		data.put("Luton".toUpperCase(),"cE06000032");
		data.put("Southend-on-Sea".toUpperCase(),"cE06000033");
		data.put("Thurrock".toUpperCase(),"cE06000034");
		data.put("Medway".toUpperCase(),"cE06000035");
		data.put("Bracknell Forest".toUpperCase(),"cE06000036");
		data.put("West Berkshire".toUpperCase(),"cE06000037");
		data.put("Reading".toUpperCase(),"cE06000038");
		data.put("Slough".toUpperCase(),"cE06000039");
		data.put("Windsor and Maidenhead".toUpperCase(),"cE06000040");
		data.put("Wokingham".toUpperCase(),"cE06000041");
		data.put("Milton Keynes".toUpperCase(),"cE06000042");
		data.put("Brighton and Hove".toUpperCase(),"cE06000043");
		data.put("Portsmouth".toUpperCase(),"cE06000044");
		data.put("Southampton".toUpperCase(),"cE06000045");
		data.put("Isle of Wight".toUpperCase(),"cE06000046");
		data.put("County Durham".toUpperCase(),"cE06000047");
		data.put("Cheshire East".toUpperCase(),"cE06000049");
		data.put("Cheshire West and Chester".toUpperCase(),"cE06000050");
		data.put("Shropshire".toUpperCase(),"cE06000051");
		data.put("Cornwall".toUpperCase(),"cE06000052");
		data.put("Isles of Scilly".toUpperCase(),"cE06000053");
		data.put("Wiltshire".toUpperCase(),"cE06000054");
		data.put("Bedford".toUpperCase(),"cE06000055");
		data.put("Central Bedfordshire".toUpperCase(),"cE06000056");
		data.put("Northumberland".toUpperCase(),"cE06000057");
		data.put("Aylesbury Vale".toUpperCase(),"cE07000004");
		data.put("Chiltern".toUpperCase(),"cE07000005");
		data.put("South Bucks".toUpperCase(),"cE07000006");
		data.put("Wycombe".toUpperCase(),"cE07000007");
		data.put("Cambridge".toUpperCase(),"cE07000008");
		data.put("East Cambridgeshire".toUpperCase(),"cE07000009");
		data.put("Fenland".toUpperCase(),"cE07000010");
		data.put("Huntingdonshire".toUpperCase(),"cE07000011");
		data.put("South Cambridgeshire".toUpperCase(),"cE07000012");
		data.put("Allerdale".toUpperCase(),"cE07000026");
		data.put("Barrow-in-Furness".toUpperCase(),"cE07000027");
		data.put("Carlisle".toUpperCase(),"cE07000028");
		data.put("Copeland".toUpperCase(),"cE07000029");
		data.put("Eden".toUpperCase(),"cE07000030");
		data.put("South Lakeland".toUpperCase(),"cE07000031");
		data.put("Amber Valley".toUpperCase(),"cE07000032");
		data.put("Bolsover".toUpperCase(),"cE07000033");
		data.put("Chesterfield".toUpperCase(),"cE07000034");
		data.put("Derbyshire Dales".toUpperCase(),"cE07000035");
		data.put("Erewash".toUpperCase(),"cE07000036");
		data.put("High Peak".toUpperCase(),"cE07000037");
		data.put("North East Derbyshire".toUpperCase(),"cE07000038");
		data.put("South Derbyshire".toUpperCase(),"cE07000039");
		data.put("East Devon".toUpperCase(),"cE07000040");
		data.put("Exeter".toUpperCase(),"cE07000041");
		data.put("Mid Devon".toUpperCase(),"cE07000042");
		data.put("North Devon".toUpperCase(),"cE07000043");
		data.put("South Hams".toUpperCase(),"cE07000044");
		data.put("Teignbridge".toUpperCase(),"cE07000045");
		data.put("Torridge".toUpperCase(),"cE07000046");
		data.put("West Devon".toUpperCase(),"cE07000047");
		data.put("Christchurch".toUpperCase(),"cE07000048");
		data.put("East Dorset".toUpperCase(),"cE07000049");
		data.put("North Dorset".toUpperCase(),"cE07000050");
		data.put("Purbeck".toUpperCase(),"cE07000051");
		data.put("West Dorset".toUpperCase(),"cE07000052");
		data.put("Weymouth and Portland".toUpperCase(),"cE07000053");
		data.put("Eastbourne".toUpperCase(),"cE07000061");
		data.put("Hastings".toUpperCase(),"cE07000062");
		data.put("Lewes".toUpperCase(),"cE07000063");
		data.put("Rother".toUpperCase(),"cE07000064");
		data.put("Wealden".toUpperCase(),"cE07000065");
		data.put("Basildon".toUpperCase(),"cE07000066");
		data.put("Braintree".toUpperCase(),"cE07000067");
		data.put("Brentwood".toUpperCase(),"cE07000068");
		data.put("Castle Point".toUpperCase(),"cE07000069");
		data.put("Chelmsford".toUpperCase(),"cE07000070");
		data.put("Colchester".toUpperCase(),"cE07000071");
		data.put("Epping Forest".toUpperCase(),"cE07000072");
		data.put("Harlow".toUpperCase(),"cE07000073");
		data.put("Maldon".toUpperCase(),"cE07000074");
		data.put("Rochford".toUpperCase(),"cE07000075");
		data.put("Tendring".toUpperCase(),"cE07000076");
		data.put("Uttlesford".toUpperCase(),"cE07000077");
		data.put("Cheltenham".toUpperCase(),"cE07000078");
		data.put("Cotswold".toUpperCase(),"cE07000079");
		data.put("Forest of Dean".toUpperCase(),"cE07000080");
		data.put("Gloucester".toUpperCase(),"cE07000081");
		data.put("Stroud".toUpperCase(),"cE07000082");
		data.put("Tewkesbury".toUpperCase(),"cE07000083");
		data.put("Basingstoke and Deane".toUpperCase(),"cE07000084");
		data.put("East Hampshire".toUpperCase(),"cE07000085");
		data.put("Eastleigh".toUpperCase(),"cE07000086");
		data.put("Fareham".toUpperCase(),"cE07000087");
		data.put("Gosport".toUpperCase(),"cE07000088");
		data.put("Hart".toUpperCase(),"cE07000089");
		data.put("Havant".toUpperCase(),"cE07000090");
		data.put("New Forest".toUpperCase(),"cE07000091");
		data.put("Rushmoor".toUpperCase(),"cE07000092");
		data.put("Test Valley".toUpperCase(),"cE07000093");
		data.put("Winchester".toUpperCase(),"cE07000094");
		data.put("Broxbourne".toUpperCase(),"cE07000095");
		data.put("Dacorum".toUpperCase(),"cE07000096");
		data.put("Hertsmere".toUpperCase(),"cE07000098");
		data.put("North Hertfordshire".toUpperCase(),"cE07000099");
		data.put("Three Rivers".toUpperCase(),"cE07000102");
		data.put("Watford".toUpperCase(),"cE07000103");
		data.put("Ashford".toUpperCase(),"cE07000105");
		data.put("Canterbury".toUpperCase(),"cE07000106");
		data.put("Dartford".toUpperCase(),"cE07000107");
		data.put("Dover".toUpperCase(),"cE07000108");
		data.put("Gravesham".toUpperCase(),"cE07000109");
		data.put("Maidstone".toUpperCase(),"cE07000110");
		data.put("Sevenoaks".toUpperCase(),"cE07000111");
		data.put("Shepway".toUpperCase(),"cE07000112");
		data.put("Swale".toUpperCase(),"cE07000113");
		data.put("Thanet".toUpperCase(),"cE07000114");
		data.put("Tonbridge and Malling".toUpperCase(),"cE07000115");
		data.put("Tunbridge Wells".toUpperCase(),"cE07000116");
		data.put("Burnley".toUpperCase(),"cE07000117");
		data.put("Chorley".toUpperCase(),"cE07000118");
		data.put("Fylde".toUpperCase(),"cE07000119");
		data.put("Hyndburn".toUpperCase(),"cE07000120");
		data.put("Lancaster".toUpperCase(),"cE07000121");
		data.put("Pendle".toUpperCase(),"cE07000122");
		data.put("Preston".toUpperCase(),"cE07000123");
		data.put("Ribble Valley".toUpperCase(),"cE07000124");
		data.put("Rossendale".toUpperCase(),"cE07000125");
		data.put("South Ribble".toUpperCase(),"cE07000126");
		data.put("West Lancashire".toUpperCase(),"cE07000127");
		data.put("Wyre".toUpperCase(),"cE07000128");
		data.put("Blaby".toUpperCase(),"cE07000129");
		data.put("Charnwood".toUpperCase(),"cE07000130");
		data.put("Harborough".toUpperCase(),"cE07000131");
		data.put("Hinckley and Bosworth".toUpperCase(),"cE07000132");
		data.put("Melton".toUpperCase(),"cE07000133");
		data.put("North West Leicestershire".toUpperCase(),"cE07000134");
		data.put("Oadby and Wigston".toUpperCase(),"cE07000135");
		data.put("Boston".toUpperCase(),"cE07000136");
		data.put("East Lindsey".toUpperCase(),"cE07000137");
		data.put("Lincoln".toUpperCase(),"cE07000138");
		data.put("North Kesteven".toUpperCase(),"cE07000139");
		data.put("South Holland".toUpperCase(),"cE07000140");
		data.put("South Kesteven".toUpperCase(),"cE07000141");
		data.put("West Lindsey".toUpperCase(),"cE07000142");
		data.put("Breckland".toUpperCase(),"cE07000143");
		data.put("Broadland".toUpperCase(),"cE07000144");
		data.put("Great Yarmouth".toUpperCase(),"cE07000145");
		data.put("King's Lynn and West Norfolk".toUpperCase(),"cE07000146");
		data.put("North Norfolk".toUpperCase(),"cE07000147");
		data.put("Norwich".toUpperCase(),"cE07000148");
		data.put("South Norfolk".toUpperCase(),"cE07000149");
		data.put("Corby".toUpperCase(),"cE07000150");
		data.put("Daventry".toUpperCase(),"cE07000151");
		data.put("East Northamptonshire".toUpperCase(),"cE07000152");
		data.put("Kettering".toUpperCase(),"cE07000153");
		data.put("Northampton".toUpperCase(),"cE07000154");
		data.put("South Northamptonshire".toUpperCase(),"cE07000155");
		data.put("Wellingborough".toUpperCase(),"cE07000156");
		data.put("Craven".toUpperCase(),"cE07000163");
		data.put("Hambleton".toUpperCase(),"cE07000164");
		data.put("Harrogate".toUpperCase(),"cE07000165");
		data.put("Richmondshire".toUpperCase(),"cE07000166");
		data.put("Ryedale".toUpperCase(),"cE07000167");
		data.put("Scarborough".toUpperCase(),"cE07000168");
		data.put("Selby".toUpperCase(),"cE07000169");
		data.put("Ashfield".toUpperCase(),"cE07000170");
		data.put("Bassetlaw".toUpperCase(),"cE07000171");
		data.put("Broxtowe".toUpperCase(),"cE07000172");
		data.put("Gedling".toUpperCase(),"cE07000173");
		data.put("Mansfield".toUpperCase(),"cE07000174");
		data.put("Newark and Sherwood".toUpperCase(),"cE07000175");
		data.put("Rushcliffe".toUpperCase(),"cE07000176");
		data.put("Cherwell".toUpperCase(),"cE07000177");
		data.put("Oxford".toUpperCase(),"cE07000178");
		data.put("South Oxfordshire".toUpperCase(),"cE07000179");
		data.put("Vale of White Horse".toUpperCase(),"cE07000180");
		data.put("West Oxfordshire".toUpperCase(),"cE07000181");
		data.put("Mendip".toUpperCase(),"cE07000187");
		data.put("Sedgemoor".toUpperCase(),"cE07000188");
		data.put("South Somerset".toUpperCase(),"cE07000189");
		data.put("Taunton Deane".toUpperCase(),"cE07000190");
		data.put("West Somerset".toUpperCase(),"cE07000191");
		data.put("Cannock Chase".toUpperCase(),"cE07000192");
		data.put("East Staffordshire".toUpperCase(),"cE07000193");
		data.put("Lichfield".toUpperCase(),"cE07000194");
		data.put("Newcastle-under-Lyme".toUpperCase(),"cE07000195");
		data.put("South Staffordshire".toUpperCase(),"cE07000196");
		data.put("Stafford".toUpperCase(),"cE07000197");
		data.put("Staffordshire Moorlands".toUpperCase(),"cE07000198");
		data.put("Tamworth".toUpperCase(),"cE07000199");
		data.put("Babergh".toUpperCase(),"cE07000200");
		data.put("Forest Heath".toUpperCase(),"cE07000201");
		data.put("Ipswich".toUpperCase(),"cE07000202");
		data.put("Mid Suffolk".toUpperCase(),"cE07000203");
		data.put("St Edmundsbury".toUpperCase(),"cE07000204");
		data.put("Suffolk Coastal".toUpperCase(),"cE07000205");
		data.put("Waveney".toUpperCase(),"cE07000206");
		data.put("Elmbridge".toUpperCase(),"cE07000207");
		data.put("Epsom and Ewell".toUpperCase(),"cE07000208");
		data.put("Guildford".toUpperCase(),"cE07000209");
		data.put("Mole Valley".toUpperCase(),"cE07000210");
		data.put("Reigate and Banstead".toUpperCase(),"cE07000211");
		data.put("Runnymede".toUpperCase(),"cE07000212");
		data.put("Spelthorne".toUpperCase(),"cE07000213");
		data.put("Surrey Heath".toUpperCase(),"cE07000214");
		data.put("Tandridge".toUpperCase(),"cE07000215");
		data.put("Waverley".toUpperCase(),"cE07000216");
		data.put("Woking".toUpperCase(),"cE07000217");
		data.put("North Warwickshire".toUpperCase(),"cE07000218");
		data.put("Nuneaton and Bedworth".toUpperCase(),"cE07000219");
		data.put("Rugby".toUpperCase(),"cE07000220");
		data.put("Stratford-on-Avon".toUpperCase(),"cE07000221");
		data.put("Warwick".toUpperCase(),"cE07000222");
		data.put("Adur".toUpperCase(),"cE07000223");
		data.put("Arun".toUpperCase(),"cE07000224");
		data.put("Chichester".toUpperCase(),"cE07000225");
		data.put("Crawley".toUpperCase(),"cE07000226");
		data.put("Horsham".toUpperCase(),"cE07000227");
		data.put("Mid Sussex".toUpperCase(),"cE07000228");
		data.put("Worthing".toUpperCase(),"cE07000229");
		data.put("Bromsgrove".toUpperCase(),"cE07000234");
		data.put("Malvern Hills".toUpperCase(),"cE07000235");
		data.put("Redditch".toUpperCase(),"cE07000236");
		data.put("Worcester".toUpperCase(),"cE07000237");
		data.put("Wychavon".toUpperCase(),"cE07000238");
		data.put("Wyre Forest".toUpperCase(),"cE07000239");
		data.put("St Albans".toUpperCase(),"cE07000240");
		data.put("Welwyn Hatfield".toUpperCase(),"cE07000241");
		data.put("East Hertfordshire".toUpperCase(),"cE07000242");
		data.put("Stevenage".toUpperCase(),"cE07000243");
		data.put("Bolton".toUpperCase(),"cE08000001");
		data.put("Bury".toUpperCase(),"cE08000002");
		data.put("Manchester".toUpperCase(),"cE08000003");
		data.put("Oldham".toUpperCase(),"cE08000004");
		data.put("Rochdale".toUpperCase(),"cE08000005");
		data.put("Salford".toUpperCase(),"cE08000006");
		data.put("Stockport".toUpperCase(),"cE08000007");
		data.put("Tameside".toUpperCase(),"cE08000008");
		data.put("Trafford".toUpperCase(),"cE08000009");
		data.put("Wigan".toUpperCase(),"cE08000010");
		data.put("Knowsley".toUpperCase(),"cE08000011");
		data.put("Liverpool".toUpperCase(),"cE08000012");
		data.put("St. Helens".toUpperCase(),"cE08000013");
		data.put("Sefton".toUpperCase(),"cE08000014");
		data.put("Wirral".toUpperCase(),"cE08000015");
		data.put("Barnsley".toUpperCase(),"cE08000016");
		data.put("Doncaster".toUpperCase(),"cE08000017");
		data.put("Rotherham".toUpperCase(),"cE08000018");
		data.put("Sheffield".toUpperCase(),"cE08000019");
		data.put("Newcastle upon Tyne".toUpperCase(),"cE08000021");
		data.put("North Tyneside".toUpperCase(),"cE08000022");
		data.put("South Tyneside".toUpperCase(),"cE08000023");
		data.put("Sunderland".toUpperCase(),"cE08000024");
		data.put("Birmingham".toUpperCase(),"cE08000025");
		data.put("Coventry".toUpperCase(),"cE08000026");
		data.put("Dudley".toUpperCase(),"cE08000027");
		data.put("Sandwell".toUpperCase(),"cE08000028");
		data.put("Solihull".toUpperCase(),"cE08000029");
		data.put("Walsall".toUpperCase(),"cE08000030");
		data.put("Wolverhampton".toUpperCase(),"cE08000031");
		data.put("Bradford".toUpperCase(),"cE08000032");
		data.put("Calderdale".toUpperCase(),"cE08000033");
		data.put("Kirklees".toUpperCase(),"cE08000034");
		data.put("Leeds".toUpperCase(),"cE08000035");
		data.put("Wakefield".toUpperCase(),"cE08000036");
		data.put("Gateshead".toUpperCase(),"cE08000037");
		data.put("City of London".toUpperCase(),"cE09000001");
		data.put("Barking and Dagenham".toUpperCase(),"cE09000002");
		data.put("Barnet".toUpperCase(),"cE09000003");
		data.put("Bexley".toUpperCase(),"cE09000004");
		data.put("Brent".toUpperCase(),"cE09000005");
		data.put("Bromley".toUpperCase(),"cE09000006");
		data.put("Camden".toUpperCase(),"cE09000007");
		data.put("Croydon".toUpperCase(),"cE09000008");
		data.put("Ealing".toUpperCase(),"cE09000009");
		data.put("Enfield".toUpperCase(),"cE09000010");
		data.put("Greenwich".toUpperCase(),"cE09000011");
		data.put("Hackney".toUpperCase(),"cE09000012");
		data.put("Hammersmith and Fulham".toUpperCase(),"cE09000013");
		data.put("Haringey".toUpperCase(),"cE09000014");
		data.put("Harrow".toUpperCase(),"cE09000015");
		data.put("Havering".toUpperCase(),"cE09000016");
		data.put("Hillingdon".toUpperCase(),"cE09000017");
		data.put("Hounslow".toUpperCase(),"cE09000018");
		data.put("Islington".toUpperCase(),"cE09000019");
		data.put("Kensington and Chelsea".toUpperCase(),"cE09000020");
		data.put("Kingston upon Thames".toUpperCase(),"cE09000021");
		data.put("Lambeth".toUpperCase(),"cE09000022");
		data.put("Lewisham".toUpperCase(),"cE09000023");
		data.put("Merton".toUpperCase(),"cE09000024");
		data.put("Newham".toUpperCase(),"cE09000025");
		data.put("Redbridge".toUpperCase(),"cE09000026");
		data.put("Richmond upon Thames".toUpperCase(),"cE09000027");
		data.put("Southwark".toUpperCase(),"cE09000028");
		data.put("Sutton".toUpperCase(),"cE09000029");
		data.put("Tower Hamlets".toUpperCase(),"cE09000030");
		data.put("Waltham Forest".toUpperCase(),"cE09000031");
		data.put("Wandsworth".toUpperCase(),"cE09000032");
		data.put("Westminster".toUpperCase(),"cE09000033");
		data.put("Clackmannanshire".toUpperCase(),"cS12000005");
		data.put("Dumfries and Galloway".toUpperCase(),"cS12000006");
		data.put("East Ayrshire".toUpperCase(),"cS12000008");
		data.put("East Lothian".toUpperCase(),"cS12000010");
		data.put("East Renfrewshire".toUpperCase(),"cS12000011");
		data.put("Eilean Siar".toUpperCase(),"cS12000013");
		data.put("Falkirk".toUpperCase(),"cS12000014");
		data.put("Fife".toUpperCase(),"cS12000015");
		data.put("Highland".toUpperCase(),"cS12000017");
		data.put("Inverclyde".toUpperCase(),"cS12000018");
		data.put("Midlothian".toUpperCase(),"cS12000019");
		data.put("Moray".toUpperCase(),"cS12000020");
		data.put("North Ayrshire".toUpperCase(),"cS12000021");
		data.put("Orkney Islands".toUpperCase(),"cS12000023");
		data.put("Perth and Kinross".toUpperCase(),"cS12000024");
		data.put("Scottish Borders".toUpperCase(),"cS12000026");
		data.put("Shetland Islands".toUpperCase(),"cS12000027");
		data.put("South Ayrshire".toUpperCase(),"cS12000028");
		data.put("South Lanarkshire".toUpperCase(),"cS12000029");
		data.put("Stirling".toUpperCase(),"cS12000030");
		data.put("Aberdeen City".toUpperCase(),"cS12000033");
		data.put("Aberdeenshire".toUpperCase(),"cS12000034");
		data.put("Argyll and Bute".toUpperCase(),"cS12000035");
		data.put("City of Edinburgh".toUpperCase(),"cS12000036");
		data.put("Renfrewshire".toUpperCase(),"cS12000038");
		data.put("West Dunbartonshire".toUpperCase(),"cS12000039");
		data.put("West Lothian".toUpperCase(),"cS12000040");
		data.put("Angus".toUpperCase(),"cS12000041");
		data.put("Dundee City".toUpperCase(),"cS12000042");
		data.put("North Lanarkshire".toUpperCase(),"cS12000044");
		data.put("East Dunbartonshire".toUpperCase(),"cS12000045");
		data.put("Glasgow City".toUpperCase(),"cS12000046");
		data.put("Isle of Anglesey".toUpperCase(),"cW06000001");
		data.put("Gwynedd".toUpperCase(),"cW06000002");
		data.put("Conwy".toUpperCase(),"cW06000003");
		data.put("Denbighshire".toUpperCase(),"cW06000004");
		data.put("Flintshire".toUpperCase(),"cW06000005");
		data.put("Wrexham".toUpperCase(),"cW06000006");
		data.put("Ceredigion".toUpperCase(),"cW06000008");
		data.put("Pembrokeshire".toUpperCase(),"cW06000009");
		data.put("Carmarthenshire".toUpperCase(),"cW06000010");
		data.put("Swansea".toUpperCase(),"cW06000011");
		data.put("Neath Port Talbot".toUpperCase(),"cW06000012");
		data.put("Bridgend".toUpperCase(),"cW06000013");
		data.put("Vale of Glamorgan".toUpperCase(),"cW06000014");
		data.put("Cardiff".toUpperCase(),"cW06000015");
		data.put("Rhondda Cynon Taf".toUpperCase(),"cW06000016");
		data.put("Caerphilly".toUpperCase(),"cW06000018");
		data.put("Blaenau Gwent".toUpperCase(),"cW06000019");
		data.put("Torfaen".toUpperCase(),"cW06000020");
		data.put("Monmouthshire".toUpperCase(),"cW06000021");
		data.put("Newport".toUpperCase(),"cW06000022");
		data.put("Powys".toUpperCase(),"cW06000023");
		data.put("Merthyr Tydfil".toUpperCase(),"cW06000024");

	}
}