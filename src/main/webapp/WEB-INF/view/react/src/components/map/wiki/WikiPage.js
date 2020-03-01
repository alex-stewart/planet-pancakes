import React, {Component} from 'react';
import WikiSection from './WikiSection';
import SettlementsWikiSection from './SettlementsWikiSection';

export default class WikiPage extends Component {

    render() {
        let hasSettlements = this.props.island && (this.props.island.cities != null || this.props.island.towns != null);
        if (this.props.island && this.props.island.wiki) {
            return [
                <div className={"h1"}>{this.props.island.name}</div>,
                <WikiSection section={this.props.island.wiki} depth={1}/>,
                hasSettlements ? <div className={"h2"}>Settlements</div> : null,
                <SettlementsWikiSection title={"Cities"} settlements={this.props.island.cities}/>,
                <SettlementsWikiSection title={"Towns"} settlements={this.props.island.towns}/>
            ]
        } else {
            return null;
        }
    }
}
