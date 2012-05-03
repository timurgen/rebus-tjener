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
var arrayName;
var arrayText;
var arrayLocation;
var arrayRadius;
var markers;
function initialize() {
    number = 1;
    arrayName = new Array();
    arrayText = new Array();
    arrayLocation = new Array();
    arrayRadius = new Array();
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
    var radius = document.getElementById("pointRadius").value;
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
    arrayName[number] = name;
    arrayText[number] = rebus;
    arrayLocation[number] = loc;
    arrayRadius[number] = radius;
    infoWindow.setContent("Added successfully");
    number+=1;

}

function saveGame() {
    //sender a big big forespørsel til tjener
}