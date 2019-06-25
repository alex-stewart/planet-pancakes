import React, {Component} from 'react';
import {Map, LayersControl, LayerGroup, ImageOverlay, Marker, Popup} from 'react-leaflet';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faAtlas, faSearchLocation} from '@fortawesome/free-solid-svg-icons'
import axios from 'axios';
import Leaflet from 'leaflet'
import {ListGroup, ListGroupItem} from 'reactstrap';
import {LatLng, DivIcon, Point} from 'leaflet/dist/leaflet-src.esm';

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
                        islands: result.data.map(this.calculateIslandPosition)
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
        let radians = (Math.PI / 180) * island.bearing;
        let islandY = island.radius * Math.cos(radians);
        let islandX = island.radius * Math.sin(radians);
        island.position = [islandY, islandX];

        let bottomLeft = new LatLng(islandY - island.size, islandX - island.size);
        let topRight = new LatLng(islandY + island.size, islandX + island.size);
        island.bounds = [bottomLeft, topRight];
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
        return <ImageOverlay key={'island-map-item-' + island.id}
                             url={'/islands/island_' + island.id + '.svg'}
                             bounds={island.bounds}/>
    }

    generateIslandMarker(island) {
        let divIcon = new DivIcon({
            iconSize: new Point(500, 10),
            className: "map-island-label",
            html: island.name
        });

        let islandPosition = [
            island.position[0] - island.size,
            island.position[1]
        ];

        return <Marker key={'island-marker-' + island.id}
                       position={islandPosition}
                       icon={divIcon}/>
    }

    generateCityMarkers(island) {

        if (!island.cities) {
            return null;
        }

        return island.cities.map(function (city) {
            let cityPosition = [
                island.position[0] + city.location.x,
                island.position[1] + city.location.y
            ];

            let divIcon = new DivIcon({
                iconSize: new Point(10, 10),
                className: "map-city-label",
            });

            return <Marker position={cityPosition}
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
                    <LayersControl collapsed={false}>
                        <LayersControl.Overlay name={"Islands"}
                                               checked={true}>
                            <LayerGroup>
                                {
                                    this.state.islands.map(this.generateIslandOverlay)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                        <LayersControl.Overlay name={"Labels"}
                                               checked={true}>
                            <LayerGroup>
                                {
                                    this.state.islands.map(this.generateIslandMarker)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                        <LayersControl.Overlay name={"Cities"}
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