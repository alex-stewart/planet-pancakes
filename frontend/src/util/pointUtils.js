export const rotatePoint = (pointX, pointY, centerX, centerY, bearing) => {
    let angle = (bearing) * (Math.PI / 180);
    let rotatedX = Math.cos(angle) * (pointX - centerX) - Math.sin(angle) * (pointY - centerY) + centerX;
    let rotatedY = Math.sin(angle) * (pointX - centerX) + Math.cos(angle) * (pointY - centerY) + centerY;
    return [rotatedX, rotatedY];
};