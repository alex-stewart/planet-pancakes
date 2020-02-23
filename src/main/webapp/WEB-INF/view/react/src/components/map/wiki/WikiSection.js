import React, {Component} from 'react';

export default class WikiSection extends Component {

    render() {
        let subsections = this.props.section.subsections ? this.props.section.subsections : [];
        let heading = this.props.section.heading ? this.props.section.heading : "";
        let content = this.props.section.content ? this.props.section.content : "";
        let headingStyle = 'h' + this.props.depth;

        return <div>
            <div className={headingStyle}>{heading}</div>
            <div>{content}</div>
            <div>
                {subsections.map(function(section) {
                    return <WikiSection section={section} key={section.heading} depth={this.props.depth +  1}/>
                }, this)}
            </div>
        </div>
    }
}