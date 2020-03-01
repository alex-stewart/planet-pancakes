import L from 'leaflet';

const cityIcon = new L.Icon({
    iconSize: new L.Point(20, 20),
    iconUrl: require('./img/city.png'),
    iconRetinaUrl: require('./img/city.png'),
    shadowUrl: null,
    shadowSize: null,
    shadowAnchor: null,
});

const townIcon = new L.Icon({
    iconSize: new L.Point(20, 20),
    iconUrl: require('./img/town.png'),
    iconRetinaUrl: require('./img/town.png'),
    shadowUrl: null,
    shadowSize: null,
    shadowAnchor: null,
});

export {cityIcon, townIcon};
