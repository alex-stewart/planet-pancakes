import L from 'leaflet';

const cityIcon = new L.Icon({
    iconSize: new L.Point(40, 40),
    iconUrl: require('./img/city.svg'),
    iconRetinaUrl: require('./img/city.svg'),
    shadowUrl: null,
    shadowSize: null,
    shadowAnchor: null,
});

const townIcon = new L.Icon({
    iconSize: new L.Point(40, 40),
    iconUrl: require('./img/town.svg'),
    iconRetinaUrl: require('./img/town.svg'),
    shadowUrl: null,
    shadowSize: null,
    shadowAnchor: null,
});

export {cityIcon, townIcon};
