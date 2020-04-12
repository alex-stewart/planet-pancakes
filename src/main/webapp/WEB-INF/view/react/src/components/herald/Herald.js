import React, {Component} from 'react';
import {Helmet} from "react-helmet";
import {Button, ButtonGroup, Input} from "reactstrap";
import ColorPicker from "./ColorPicker";

const heraldPageStyle = {
    backgroundColor: '#F8ECC2',
    height: 'calc(100% - 56px)',
    paddingTop: '50px',
    paddingLeft: '10%',
    paddingRight: '10%',
    margin: 'auto',
    overflowY: 'scroll'
};

const inputGroupStyle = {
    display: 'flex',
    padding: '0px'
};

const heraldComponents = [
    {
        size: 0,
        name: 'Tiny',
        x: 0,
        y: 0,
        length: 100
    }, {
        size: 1,
        name: 'Small',
        x: 0,
        y: 100,
        length: 100
    }, {
        size: 2,
        name: 'Medium',
        x: 100,
        y: 0,
        length: 200
    }, {
        size: 3,
        name: 'Large',
        x: 0,
        y: 200,
        length: 300
    }, {
        size: 4,
        name: 'Very Large',
        x: 300,
        y: 0,
        length: 500
    }
];

export default class Herald extends Component {

    constructor(props) {
        super(props);
        this.state = {
            arms: {
                size: 0,
                components: heraldComponents.map(() => {
                    return {'backgroundColour': '#FFFFFF'}
                })
            }
        };
    }

    componentDidMount() {
        let ctx = this.refs.canvas.getContext('2d');
        this.setState({
            canvasContext: ctx
        });
        this.draw(ctx, this.state.arms)
    }

    draw(ctx, arms) {
        ctx.clearRect(0, 0, this.refs.canvas.width, this.refs.canvas.height);

        ctx.beginPath();
        heraldComponents.forEach(h => {
            if (h.size <= arms.size) {
                ctx.rect(h.x, h.y, h.length, h.length);
                ctx.fillStyle = arms.components[h.size].backgroundColour;
                ctx.fillRect(h.x, h.y, h.length, h.length);
            }
        });
        ctx.stroke();
    }

    setSize(size) {
        let arms = this.state.arms;
        arms.size = size;
        this.setState({arms: arms});
        this.draw(this.state.canvasContext, arms);
    };

    sizeSelectButton(heraldComponents) {
        return <Button color="dark"
                       onClick={() => this.setSize(heraldComponents.size)}
                       key={'size-button-' + heraldComponents.size}>
            {heraldComponents.name}
        </Button>
    }

    setBackgroundColour(colour, size) {
        let arms = this.state.arms;
        arms.components[size].backgroundColour = colour;
        this.setState({arms: arms});
        this.draw(this.state.canvasContext, this.state.arms);
    }

    contentDropdown(component) {
        let size = component.size;

        if (this.state.arms.size < size) {
            return null
        } else {
            return <div style={inputGroupStyle}
                        key={'content-dropdown-' + size}>
                {size}.
                <ColorPicker setColour={this.setBackgroundColour.bind(this)} size={size}/>
                <Input style={{width: '200px'}} type="select" name="select" id="contentShape">
                    <option>Blank</option>
                    <option>Square</option>
                    <option>Circle</option>
                    <option>Rectangle</option>
                    <option>Triangle</option>
                </Input>
            </div>
        }
    }

    render() {
        return (
            <div style={heraldPageStyle}>
                <Helmet>
                    <title>{"PP - Herald"}</title>
                </Helmet>
                <div>
                    <ButtonGroup color="dark">
                        {
                            heraldComponents.map(this.sizeSelectButton.bind(this))
                        }
                    </ButtonGroup>
                </div>
                <div style={{display: 'flex'}}>
                    <div>
                        {
                            heraldComponents.map(this.contentDropdown.bind(this))
                        }
                    </div>
                    <canvas ref={"canvas"}
                            style={{padding: '10px'}}
                            width={1000}
                            height={1000}/>
                </div>
            </div>
        )
    }
}
