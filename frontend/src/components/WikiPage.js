import React, {Component} from 'react';
import WikiSection from './WikiSection';

export default class WikiPage extends Component {

    render() {
        if (this.props.island && this.props.island.wiki) {
            return <WikiSection section={this.props.island.wiki} depth={1}/>
        } else {
            return <div>No wiki content.</div>
        }
    }
}