import React, {Component} from 'react';

export default class SettlementsWikiSection extends Component {

    settlementInfo(settlement) {
        return <div>
            <div className={"h4"}>{settlement.name}</div>
            <div>{settlement.description}</div>
        </div>
    }


    render() {
        if (this.props.settlements) {
            return <div>
                <div className={"h3"}>{this.props.title}</div>
                {
                    this.props.settlements.map(this.settlementInfo)
                }
            </div>
        } else {
            return null
        }
    }
}
