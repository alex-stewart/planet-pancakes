import React, {Component} from 'react';
import {LayerGroup, LayersControl, Map, Marker} from 'react-leaflet';
import axios from 'axios';
import Leaflet from 'leaflet';
import L from 'leaflet';
import ImageOverlayRotated from './ImageOverlayRotated';
import {rotatePoint} from '../../util/point-utils';
import SettlementMarker from "./SettlementMarker";
import {SETTLEMENT_TYPES} from "./Constants";
import _ from "lodash";
import CompassControl from "./CompassControl";
import MapSidebar from "./MapSidebar";

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
                let cityLat = island.position[0] + city.location.y;
                let cityLong = island.position[1] + city.location.x;
                city.position = rotatePoint(cityLat, cityLong, island.position[0], island.position[1], island.bearing);
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
            className: "map-text-label",
            html: island.name || ""
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
        return _.map(island.cities, function(city) {
            return <SettlementMarker settlement={city}
                                     key={"city-marker-" + city.name}
                                     type={SETTLEMENT_TYPES.CITY}/>
        });
    }

    generateTownMarkers(island) {
        return _.map(island.towns, function(town) {
            return <SettlementMarker settlement={town}
                                     key={"town-marker-" + town.name}
                                     type={SETTLEMENT_TYPES.TOWN}/>
        });
    }

    render() {

        return (
            <div className="map-container">
                <MapSidebar islands={this.state.islands}
                            focusOnIsland={this.focusOnIsland.bind(this)}/>
                <Map className={"map"}
                     bounds={this.state.bounds}
                     crs={Leaflet.CRS.Simple}
                     zoom={3}
                     ref={"map"}
                     attributionControl={false}>
                    <CompassControl/>
                    <LayerGroup key={"layer-group-map"}>
                        {
                            this.state.islands.map(this.generateIslandOverlay)
                        }
                    </LayerGroup>
                    <LayersControl>
                        <LayersControl.Overlay key={"label-overlay"}
                                               name={"Island Labels"}
                                               checked={true}>
                            <LayerGroup key={"layer-group-island-labels"}>
                                {
                                    this.state.islands.map(this.generateIslandMarker)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                        <LayersControl.Overlay key={"city-overlay"}
                                               name={"Cities"}
                                               checked={false}>
                            <LayerGroup key={"layer-group-cities"}>
                                {
                                    this.state.islands.map(this.generateCityMarkers)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                        <LayersControl.Overlay key={"town-overlay"}
                                               name={"Towns"}
                                               checked={false}>
                            <LayerGroup key={"layer-group-towns"}>
                                {
                                    this.state.islands.map(this.generateTownMarkers)
                                }
                            </LayerGroup>
                        </LayersControl.Overlay>
                    </LayersControl>
                </Map>
            </div>
        )
    }
}