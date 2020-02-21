import React, {Component} from 'react';

import {ListGroup, ListGroupItem, Modal, ModalBody} from "reactstrap";
import WikiPage from "./WikiPage";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAtlas, faSearchLocation} from "@fortawesome/free-solid-svg-icons";

export default class MapSidebar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedIsland: false
        }
    }

    setSelectedIsland(event, selectedIsland) {
        this.setState({
            selectedIsland: selectedIsland
        })
    }

    clearSelectedIsland() {
        this.setState({
            selectedIsland: null
        })
    }

    generateSideBarItem(island, setSelectedIsland, focusOnIsland) {
        if (island.name) {
            return (
                <ListGroupItem key={'island-menu-item-' + island.id}
                               className={"map-sidebar-menu-item"}>
                    {island.name}
                    <div>
                        <span className={"map-sidebar-icon"} onClick={event => setSelectedIsland(event, island)}>
                            <FontAwesomeIcon icon={faAtlas}/>
                        </span>
                        <span className={"map-sidebar-icon"} onClick={event => focusOnIsland(event, island)}>
                            <FontAwesomeIcon icon={faSearchLocation}/>
                        </span>
                    </div>
                </ListGroupItem>
            )
        }
    }

    render() {
        return (
            <ListGroup>
                {
                    this.props.islands.map(island => this.generateSideBarItem(island, this.setSelectedIsland.bind(this), this.props.focusOnIsland))
                }
                <Modal isOpen={this.state.selectedIsland}
                       toggle={this.clearSelectedIsland.bind(this)}
                       className={"island-wiki-box"}>
                    <ModalBody
                        className={"wiki-page"}>
                        <WikiPage island={this.state.selectedIsland}/>
                    </ModalBody>
                </Modal>
            </ListGroup>

        )
    }
}