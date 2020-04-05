import React, {Component} from 'react';
import {Helmet} from 'react-helmet'
import {ImageOverlay, LayerGroup, LayersControl, Map} from 'react-leaflet';
import Leaflet from 'leaflet';
import SettlementMarker from "../map/SettlementMarker";
import _ from "lodash";
import {SETTLEMENT_TYPES} from "../map/Constants";

const mapContainerStyle = {
    width: '100%',
    height: '100%',
    overflow: 'hidden'
};

const settlements = [
    {
        name: 'XXXX',
        position: [3, -15]
    }, {
        name: 'Ogg Bogg',
        position: [-45, -7]
    }, {
        name: 'Agg Bagg',
        position: [-32, -17]
    }, {
        name: 'Ugg Bugg',
        position: [-40, -30]
    }, {
        name: 'Igg Bigg',
        position: [-1, -1]
    }, {
        name: "Onetown",
        position: [-52, -47]
    }, {
        name: "Twotown",
        position: [-60, -39]
    }, {
        name: "Windwall",
        position: [-39, -89]
    }
];

export default class PanCan extends Component {

    generateSettlementMarkers() {
        return _.map(settlements, function (settlement) {
            return <SettlementMarker settlement={settlement}
                                     key={"settlement-marker-" + settlement.name}
                                     type={SETTLEMENT_TYPES.CITY}/>
        });
    }

    render() {
        return (
            <div style={mapContainerStyle}>
                <Helmet>
                    <title>{"Panton Candora"}</title>
                </Helmet>
                <Map className={"map"}
                     bounds={[[-100, -100], [100, 100]]}
                     crs={Leaflet.CRS.Simple}
                     zoom={3}
                     ref={"pan-can-map"}
                     attributionControl={false}>
                    <LayerGroup key={"layer-group-topological"}>
                        <ImageOverlay key={'image-overlay-topological'}
                                      url={require('./map/topological.svg')}
                                      bounds={[[-100, -100], [100, 100]]}/>
                    </LayerGroup>
                    <LayersControl>
                        <LayersControl.Overlay key={"base-layer-political"}
                                               name={"Political"}
                                               checked={false}>
                            <ImageOverlay key={'image-overlay-political'}
                                          url={require('./map/political.svg')}
                                          bounds={[[-100, -100], [100, 100]]}/>
                        </LayersControl.Overlay>
                        <LayersControl.Overlay key={"overlay-layer-labels"}
                                               name={"Labels"}
                                               checked={false}>
                        </LayersControl.Overlay>
                        <LayersControl.Overlay key={"overlay-layer-settlements"}
                                               name={"Settlements"}
                                               checked={true}>
                            <LayerGroup>{this.generateSettlementMarkers()}</LayerGroup>

                        </LayersControl.Overlay>
                        <LayersControl.Overlay key={"overlay-transport-network"}
                                               name={"Trade Routes"}
                                               checked={false}>
                            {/*City connections and trade routes*/}
                        </LayersControl.Overlay>
                    </LayersControl>
                </Map>
            </div>
        )
    }
}
