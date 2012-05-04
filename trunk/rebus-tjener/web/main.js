/* 
 * @author Timur Samkharadze
 * 
 */

//var contentString = ['<form id=\"pointForm\" action=\"\" onsubmit=\"false\">'+
//    '<table class=\"gamepoint\">'+
//    '<tr>'+
//    '<td>Navn:</td> '+
//    '<td><input type=\"text\" id=\"pointName\" /></td>'+
//    '</tr>'+
//    '<tr>'+
//    '<td>Rebus tekst:</td> '+
//    '<td><input type=\"text\" id=\"pointtext\" /></td>'+
//    '</tr>'+
//    '<tr>'+
//    '<td><button onclick=\"close()\">Save</button><td>'+
//    '</tr>'+
//    '</table>'+
//    '</form>'].join("");


var contentString = 
    ['<table class=\"gamepoint\">'+
    '<tr>'+
    '<td>Navn:</td> '+
    '<td><input type=\"text\" id=\"pointName\" /></td>'+
    '</tr>'+
    '<tr>'+
    '<td>Rebus tekst:</td> '+
    '<td><input type=\"text\" id=\"pointText\" /></td>'+
    '</tr>'+
    '<tr>'+
    '<td>Radius m:</td> '+
    '<td><input type=\"text\" id=\"pointRadius\" /></td>'+
    '</tr>'+
    '<tr>'+
    '<td><button onclick=\"savePoint()\">Save</button><td>'+
    '</tr>'+
    '</table>'].join("");



var infoWindow;
var loc;
var number;
var markers;
var circle;
var resultArray = {};
var mapRef;
function initialize() {
    number = 1;
    markers = new Array();
    infoWindow = new google.maps.InfoWindow({
    content: contentString
    });
    var myOptions = {
        center: new google.maps.LatLng(68.44104, 17.412028),
        zoom: 14,
        mapTypeId: google.maps.MapTypeId.HYBRID
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"),
        myOptions);
    mapRef = map;    
    //plasseser marker på map    
    google.maps.event.addListener(map, 'click', function(event) {
            infoWindow.setContent(contentString);
            placeMarker(event, map, infoWindow);
            
            
    });
    
    

}

function placeMarker(event, map,infoWindow) {
  loc = event.latLng;
  this.markers[number] = new google.maps.Marker({
      position: loc,
      map: map
  });
  infoWindow.open(map, this.markers[number]);
}

function savePoint() {
    
    var name = document.getElementById("pointName").value;
    var rebus = document.getElementById("pointText").value;
    var radius = parseInt(document.getElementById("pointRadius").value);
    if(isNaN(radius) || radius < 5){
        alert("wrong radius");
        exit;       
    }
    if(name.length == 0) {
        alert("fill out name");
        exit;
    }
    if(rebus.length == 0) {
        alert("fill out rebus text");
        exit;        
    }
    if(radius.length == 0) {
        alert("fill out radius");
        exit;          
    }
    //variabel loc inneholder nå coordinater
    //output table
    var table = document.getElementById("points");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    
    var cell1 = row.insertCell(0);
    cell1.innerHTML = number; 
    
    var cell2 = row.insertCell(1);
    cell2.innerHTML = name;
    
    var cell3 = row.insertCell(2);
    cell3.innerHTML = rebus;
    
    var cell4 = row.insertCell(3);
    cell4.innerHTML = radius;
    //legger til array
    //arrayName[number] = name;
    //arrayText[number] = rebus;
    //arrayLocation[number] = loc;
    //arrayRadius[number] = radius;
    resultArray[number] = {
        pointName: name,
        pointText: rebus,
        pointLocation: loc,
        pointRadius: radius
    }
    infoWindow.setContent("<div class=\"infoWindowMessage\">Added successfully</div>");
    constructCircles(mapRef);
    number+=1;

}

function saveGame() {
    
    //sender a big big forespørsel til tjener
    var element;
    for(var point in resultArray) {
        //felt med punktnavn
        element = document.createElement("input");
        element.setAttribute("type", "hidden");
        element.setAttribute("name", "punktname[]");
        element.setAttribute("value", resultArray[point].pointName);
        document.getElementById("addGameFormId").appendChild(element);
        
        //felt med rebustekst
        element = document.createElement("input");
        element.setAttribute("type", "hidden");
        element.setAttribute("name", "rebustekst[]");
        element.setAttribute("value", resultArray[point].pointText);
        document.getElementById("addGameFormId").appendChild(element);
        //felt med koordinater
        element = document.createElement("input");
        element.setAttribute("type", "hidden");
        element.setAttribute("name", "location[]");
        element.setAttribute("value", resultArray[point].pointLocation);
        document.getElementById("addGameFormId").appendChild(element);
        //felt med radius
        element = document.createElement("input");
        element.setAttribute("type", "hidden");
        element.setAttribute("name", "radius[]");
        element.setAttribute("value", resultArray[point].pointRadius);
        document.getElementById("addGameFormId").appendChild(element);
    }
    return true
}

function constructCircles(map) {
    
    for(var point in resultArray) {
      var circleOptions = {
        strokeColor: "#FF0000",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#FF0000",
        fillOpacity: 0.35,
        map: map,
        center: resultArray[point].pointLocation,
        radius: resultArray[point].pointRadius
     };
    circle = new google.maps.Circle(circleOptions);
    }

}
