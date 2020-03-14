import React, {Component} from 'react';
import WikiSection from './WikiSection';
import SettlementsWikiSection from './SettlementsWikiSection';
import WikiStatBlock from "./WikiStatBlock";
import {Row, Col} from 'reactstrap';

const imageStyle = {
    height: "200px",
    paddingLeft: "20px"
};

const rowStyle = {
    padding: "20px"
};

export default class WikiPage extends Component {

    settlementHeader(island) {
        if (island.cities || island.towns) {
            return <div className={"h2"}>Settlements</div>
        }
    }

    citySection(cities) {
        if (cities) {
            return <SettlementsWikiSection title={"Cities"} settlements={cities} key={"city-section"}/>

        }
    }

    townSection(towns) {
        if (towns) {
            return <SettlementsWikiSection title={"Towns"} settlements={towns} key={"town-section"}/>

        }
    }

    render() {
        if (this.props.island && this.props.island.wiki) {
            return (
                <Col>
                    <div className={"h1"}>{this.props.island.name}</div>
                    <Row style={rowStyle}>
                        <WikiStatBlock island={this.props.island}/>
                        <img src={'/flags/flag_' + this.props.island.id + '.svg'}
                             alt={this.props.island.id + "-flag"}
                             style={imageStyle}/>
                        <img src={'/islands/island_' + this.props.island.id + '.svg'}
                             alt={this.props.island.id + "-island"}
                             style={imageStyle}/>
                    </Row>
                    <WikiSection section={this.props.island.wiki} depth={1}/>
                    {this.settlementHeader(this.props.island)}
                    {this.citySection(this.props.island.cities)}
                    {this.townSection(this.props.island.towns)}
                </Col>
            )
        } else {
            return null
        }
    }
}
