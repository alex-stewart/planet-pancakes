import React, {Component} from 'react';
import {Marker, Popup, Tooltip} from "react-leaflet";
import {cityIcon, townIcon} from "./icon/SettlementIcons";
import {SETTLEMENT_TYPES} from "./Constants";

const iconMap = {
    [SETTLEMENT_TYPES.CITY]: cityIcon,
    [SETTLEMENT_TYPES.TOWN]: townIcon
};

export default class SettlementMarker extends Component {

    constructor(props) {
        super(props);
        this.state = {
            settlement: props.settlement,
            type: props.type
        };
    }

    render() {
        return <Marker key={this.state.type + "-icon-" + this.state.settlement.name}
                        position={this.state.settlement.position}
                        icon={iconMap[this.state.type]}>
            <Popup key={this.state.type + "-popup-" + this.state.settlement.name}
                   permanent={true}
                   direction={'right'}>
                <h3>{this.state.settlement.name}</h3>
                <h4>{this.state.settlement.description}</h4>
            </Popup>
            <Tooltip key={this.state.type + "-tooltip-" + this.state.settlement.name}
                     permanent={true}
                     direction={"right"}
                     className={"map-text-label"}>
                {this.state.settlement.name}
            </Tooltip>
        </Marker>
    }
}
