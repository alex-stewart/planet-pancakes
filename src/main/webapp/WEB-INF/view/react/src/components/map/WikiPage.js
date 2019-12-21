import React, {Component} from 'react';
import WikiSection from './WikiSection';

export default class WikiPage extends Component {

    render() {
        if (this.props.island && this.props.island.wiki) {
            return [
                <div className={"h1"}>{this.props.island.name}</div>,
                <WikiSection section={this.props.island.wiki} depth={1}/>
            ]
        } else {
            return null;
        }
    }
}