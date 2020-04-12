import React, {Component} from 'react'
import {GithubPicker} from 'react-color'

const containerStyle = {
    lineHeight: '0px'
};

const swatchStyle = {
    padding: '5px',
    background: '#fff',
    borderRadius: '.25rem',
    display: 'inline-block',
    border: "1px solid #ced4da",
    cursor: 'pointer',
};

const popoverStyle = {
    position: 'absolute',
    zIndex: '2',
};

const coverStyle = {
    position: 'fixed',
    top: '0px',
    right: '0px',
    bottom: '0px',
    left: '0px',
};

class ColourPicker extends Component {
    state = {
        displayColourPicker: false,
        colour: '#FFFFFF',
    };

    handleClick = () => {
        this.setState({displayColourPicker: !this.state.displayColourPicker})
    };

    handleClose = () => {
        this.setState({displayColourPicker: false})
    };

    handleChange = (colour) => {
        this.setState({displayColourPicker: false,
            colour: colour.hex});
        this.props.setColour(colour.hex, this.props.size);
    };

    render() {

        const colourStyle = {
            width: '26px',
            height: '26px',
            borderRadius: '2px',
            background: `${this.state.colour}`,
        };

        return (
            <div style={containerStyle}>
                <div style={swatchStyle} onClick={this.handleClick}>
                    <div style={colourStyle}/>
                </div>
                {this.state.displayColourPicker ? <div style={popoverStyle}>
                    <div style={coverStyle} onClick={this.handleClose}/>
                    <GithubPicker onChange={this.handleChange}/>
                </div> : null}
            </div>
        )
    }
}

export default ColourPicker;
