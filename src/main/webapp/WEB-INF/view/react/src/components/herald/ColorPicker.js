import React from 'react'
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

class ColourPicker extends React.Component {
    state = {
        displayColorPicker: false,
        color: {
            r: 'FF',
            g: 'FF',
            b: 'FF',
            a: '1',
        },
    };

    handleClick = () => {
        this.setState({displayColorPicker: !this.state.displayColorPicker})
    };

    handleClose = () => {
        this.setState({displayColorPicker: false})
    };

    handleChange = (color) => {
        this.setState({color: color.rgb})
    };

    render() {

        const colorStyle = {
            width: '26px',
            height: '26px',
            borderRadius: '2px',
            background: `rgba(${this.state.color.r}, ${this.state.color.g}, ${this.state.color.b}, ${this.state.color.a})`,
        };

        return (
            <div style={containerStyle}>
                <div style={swatchStyle} onClick={this.handleClick}>
                    <div style={colorStyle}/>
                </div>
                {this.state.displayColorPicker ? <div style={popoverStyle}>
                    <div style={coverStyle} onClick={this.handleClose}/>
                    <GithubPicker onChange={this.handleChange}/>
                </div> : null}
            </div>
        )
    }
}

export default ColourPicker;
