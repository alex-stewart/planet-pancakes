import React, {Component} from 'react';
import Control from "react-leaflet-control";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faDraftingCompass} from "@fortawesome/free-solid-svg-icons";
import {UncontrolledTooltip} from "reactstrap";
import {ReactComponent as DirectionsImage} from "./icon/img/directions.svg";

export default class CompassControl extends Component {

    render() {

        return (
            <Control position="bottomright">
                <FontAwesomeIcon id={"date-info-icon"}
                                 icon={faDraftingCompass}
                                    size={"3x"}/>
                <UncontrolledTooltip target={"date-info-icon"}
                                     placement={"top"}
                                     className={"compass-tooltip"}>
                        <DirectionsImage width={500} height={500}/>
                </UncontrolledTooltip>
            </Control>
        )
    }
}