import MapLayer from "react-leaflet/es/MapLayer";
import {withLeaflet} from "react-leaflet/es/context";
import "leaflet-imageoverlay-rotated";
import L from "leaflet";

class ImageOverlayRotated extends MapLayer {
    createLeafletElement(props) {
        const { url, topLeft, topRight, bottomLeft, opacity } = props;
        return L.imageOverlay.rotated(
            url,
            topLeft,
            topRight,
            bottomLeft,
            {
                opacity: opacity,
                interactive: false
            }
        );
    }

    componentDidUpdate({ checked }) {
        const { map } = this.props.leaflet;
        if (checked) {
            this.leafletElement.addTo(map);
        }
    }

}

export default withLeaflet(ImageOverlayRotated);