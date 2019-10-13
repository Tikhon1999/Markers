var raster = new ol.layer.Tile({
    source: new ol.source.OSM()
    //source: new ol.source.MapQuest({layer: 'sat'})
});
var googleMap =  new ol.source.TileImage({
    url: 'http://mt1.google.com/vt/lyrs=m@113&hl=en&&x={x}&y={y}&z={z}'
})

var OSM = new ol.source.OSM();

var source = new ol.source.Vector();

var wkt;

var  format = new ol.format.WKT();


var vector = new ol.layer.Vector({
    source: source,
    style: new ol.style.Style({
        fill: new ol.style.Fill({
            color: 'rgba(255, 255, 255, 0.2)'
        }),
        stroke: new ol.style.Stroke({
            color: '#ffcc33',
            width: 2
        }),
        image: new ol.style.Circle({
            radius: 7,
            fill: new ol.style.Fill({
                color: '#ffcc33'
            })
        })
    })
});

var map = new ol.Map({
    layers: [raster, vector],
    target: 'map',
    view: new ol.View({
        center: [245008.87556529557, 5093110.571802436],
        zoom: 7
    })
});

function addGoogle(){
    raster.setSource(googleMap);
}

function addOSM(){
    raster.setSource(OSM);
}

// map.addInteraction(modify);

var draw, snap, deleteSelect; // global so we can remove it later
var typeSelect = document.getElementById('type');

var coords;
window.onload = function() {
    $.ajax({
        type: "GET",
        // data: {"array":JSON.stringify(event.feature.getGeometry().getCoordinates())},
        url: "/figureselect",
        success: function (data) {
            for(var i = 0; i < data.coords.length; i++)
                setFeature(data.coords[i]);
            console.log(data);
            // console.log( data );
        },
        error: function () {
            console.log('fail');
        }
    });
};

function setFeature(wkt){
    var feature = format.readFeature(wkt);
    source.addFeature(feature);
}

var coordinates = [];
var select;
var modify;
var coord1, coord2;

var updateFeature = function () {
    select = new ol.interaction.Select({
        features: coordinates
    });
    map.addInteraction(select);

    modify = new ol.interaction.Modify({
        features: select.getFeatures()
    });

    // map.removeInteraction(draw);


    map.addInteraction(modify);

    modify.on('modifystart', function() {
        console.log(coordinates[0].getGeometry().getCoordinates());
            coord1 = format.writeGeometry(coordinates[0].getGeometry());
    });
    modify.on('modifyend', function() {
        console.log(coordinates[0].getGeometry().getCoordinates());
        coord2 =format.writeGeometry(coordinates[0].getGeometry());
        // strArray[0] = coord1;
        // strArray[1] = coord2;
        $.ajax({
            type: "POST",
            data: {
                "new": coord2,
                "old": coord1
            },
            url: "/figureupdate",
            success: function () {
                //console.log(data);
            },
            error: function () {
                console.log("errornffgf");
            }
        });
    });
};

//удаление фигуры
var DeleteFeature = function () {
    //map.removeInteraction(modify);
    deleteSelect = new ol.interaction.Select();
    map.addInteraction(deleteSelect);

    deleteSelect.getFeatures().on('add', function(feature){
        wkt = format.writeGeometry(feature.element.getGeometry());
        $.ajax({
            type: "POST",
            data: {"wkt": wkt},
            url: "/figuredelete",
            success: function () {

            },
            error: function () {
                console.log("errornffgf");
            }
        });
        source.removeFeature(feature.element);
        feature.target.remove(feature.element);
    });

};

//добавление фигуры на карту
var addFeature = function () {
    // map.removeInteraction(select);
    // map.removeInteractione(modify);
    draw = new ol.interaction.Draw({
        source: source,
        type: typeSelect.value
    });
    map.addInteraction(draw);
    snap = new ol.interaction.Snap({source: source});
    map.addInteraction(snap);
};
//добавление координат в БД при завершении рисования
var addCoordinates = function () {
    draw.on('drawend', function (event) {
        //console.log(event.feature.getGeometry().getCoordinates());
        wkt = format.writeGeometry(event.feature.getGeometry());
        $.ajax({
            type: "POST",
            data: {"wkt": wkt},
            url: "/figureinsert",
            success: function () {
                //console.log(event.feature.getGeometry().getCoordinates());
            },
            error: function () {
                console.log(event.feature.getGeometry().getCoordinates());

            }
        });
    });
};


//выполнение соотв взаимодействия
function addRequiredInteractions() {
    map.removeInteraction(deleteSelect);
    map.removeInteraction(select);
    map.removeInteraction(modify);
    var value = typeSelect.value;
    if (value !== 'None') {
        if(value==='Delete'){
            DeleteFeature();
        }else if(value==='Update'){
            updateFeature();
        }else{
            addFeature(value);
            addCoordinates();
        }

    }
}


/**
 * Let user change the geometry type.
 * @param {Event} e Change event.
 */
typeSelect.onchange = function(e) {
    map.removeInteraction(draw);
    map.removeInteraction(snap);
    addRequiredInteractions();

};
addRequiredInteractions();
