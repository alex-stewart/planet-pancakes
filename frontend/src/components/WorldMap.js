import React, {Component} from 'react';
import {Map, LayersControl, LayerGroup, Marker, Popup} from 'react-leaflet';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faAtlas, faSearchLocation} from '@fortawesome/free-solid-svg-icons'
import axios from 'axios';
import Leaflet from 'leaflet';
import L from 'leaflet';
import {ListGroup, ListGroupItem} from 'reactstrap';
import ImageOverlayRotated from './ImageOverlayRotated';
import {rotatePoint} from '../util/pointUtils';

export default class WorldMap extends Component {

    constructor(props) {
        super(props);
        this.state = {
            islands: [],
            bounds: [[-100, -100], [100, 100]],
            error: null
        }
    }

    componentDidMount() {
        this.getIslands();
        this.addMapListeners();
    }

    getIslands() {
        axios.get("/api/islands")
            .then(
                (result) => {
                    this.setState({
                        islands: result.data
                            .map(this.calculateIslandPosition)
                            .map(this.calculateIslandRotation)
                            .map(this.calculateCityPositionsForIsland)
                    });
                },
                (error) => {
                    this.setState({
                        error
                    });
                }
            )
    }

    calculateIslandPosition(island) {
        island.position = rotatePoint(island.radius, 0, 0, 0, island.bearing);
        let bottomLeft = new L.LatLng(island.position[0] - island.size, island.position[1] - island.size);
        let topRight = new L.LatLng(island.position[0] + island.size, island.position[1] + island.size);
        island.bounds = [bottomLeft, topRight];
        return island;
    }

    calculateIslandRotation(island) {
        island.bottomLeft = rotatePoint(island.bounds[0].lat, island.bounds[0].lng, island.position[0], island.position[1], island.bearing);
        island.topLeft = rotatePoint(island.bounds[1].lat, island.bounds[0].lng, island.position[0], island.position[1], island.bearing);
        island.topRight = rotatePoint(island.bounds[1].lat, island.bounds[1].lng, island.position[0], island.position[1], island.bearing);
        return island;
    }

    calculateCityPositionsForIsland(island) {
        if (island.cities) {
            island.cities.forEach(city => {
                let cityX = island.position[0] + city.location.x;
                let cityY = island.position[1] + city.location.y;
                city.position = rotatePoint(cityX, cityY, island.position[0], island.position[1], island.bearing);
                return city;
            })
        }
        return island;
    }

    addMapListeners = () => {
        if (this.refs.map) {
            this.refs.map.leafletElement.on('zoomstart', this.resetBounds);
            this.refs.map.leafletElement.on('movestart', this.resetBounds);
        }
    };

    resetBounds = () => {
        if (this.state.bounds) {
            this.setState({
                bounds: null
            });
        }
    };

    focusOnIsland(event, island) {
        this.setState({
            bounds: island.bounds
        });
    }

    generateIslandOverlay(island) {
        return <ImageOverlayRotated key={'island-map-item-' + island.id}
                                    url={'/islands/island_' + island.id + '.svg'}
                                    topLeft={island.topLeft}
                                    topRight={island.topRight}
                                    bottomLeft={island.bottomLeft}/>
    }

    generateIslandMarker(island) {
        let divIcon = new L.DivIcon({
            iconSize: new L.Point(500, 10),
            className: "map-island-label",
            html: island.name
        });

        let islandLabelPosition = [
            island.position[0] - island.size,
            island.position[1]
        ];

        return <Marker key={'island-marker-' + island.id}
                       position={islandLabelPosition}
                       icon={divIcon}/>
    }

    generateCityMarkers(island) {
        if (!island.cities) {
            return null;
        }

        return island.cities.map(function (city) {
            let divIcon = new L.DivIcon({
                iconSize: new L.Point(10, 10),
                className: "map-city-label",
            });

            return <Marker key={"city-icon-" + city.name}
                           position={city.position}
                           icon={divIcon}>
                <Popup>
                    <h3>{city.name}</h3>
                    <h5>{city.description}</h5>
                </Popup>
            </Marker>
        });
    }

    generateSideBarItem(island) {
        return (
            <ListGroupItem key={'island-menu-item-' + island.id}
                           className={"map-sidebar-menu-item"}>
                {island.name}
                <div>
                    <span className={"map-sidebar-icon"}>
                        <FontAwesomeIcon icon={faAtlas}/>
                    </span>
                    <span className={"map-sidebar-icon"} onClick={event => this.focusOnIsland(event, island)}>
                        <FontAwesomeIcon icon={faSearchLocation}/>
                    </span>
                </div>
            </ListGroupItem>
        )
    }

    render() {
        return (
            <div className="map-container">
                <ListGroup className="map-sidebar">
                    {
                        this.state.islands.map(this.generateSideBarItem.bind(this))
                    }
                </ListGroup>
                <Map className={"map"}
                     bounds={this.state.bounds}
                     crs={Leaflet.CRS.Simple}
                     zoom={3}
                     ref={"map"}>
                    <LayerGroup>
                        {
                            this.state.islands.map(this.generateIslandOverlay)
                        }
                    </LayerGroup>
                    <LayersControl>
                        <LayersControl.Overlay key={"label-overlay"}
                                               name={"Island Labels"}
                                               checked={true}>
                            <LayerGroup>
                                {
                                    this.state.islands.map(this.generateIslandMarker)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                        <LayersControl.Overlay key={"city-overlay"}
                                               name={"Cities"}
                                               checked={false}>
                            <LayerGroup>
                                {
                                    this.state.islands.map(this.generateCityMarkers)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                    </LayersControl>
                </Map>
            </div>
        )
    }
}