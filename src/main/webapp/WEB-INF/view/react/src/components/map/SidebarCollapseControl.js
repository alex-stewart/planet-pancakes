import React, {Component} from 'react';
import Control from "react-leaflet-control";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAngleRight, faAngleLeft} from "@fortawesome/free-solid-svg-icons";

export default class SidebarCollapseControl extends Component {

    buttonIcon(sidebarVisible) {
        if (sidebarVisible) {
            return faAngleLeft
        } else {
            return faAngleRight
        }
    }

    render() {
        return (
            <Control position="bottomleft">
                <div onClick={this.props.toggleSidebar}>
                    <FontAwesomeIcon icon={this.buttonIcon(this.props.sidebarVisible)}
                                     size={"3x"}
                                     className={"map-sidebar-toggle-icon"}
                    transform={"data-fa-transform"}/>
                </div>
            </Control>
        )
    }
}