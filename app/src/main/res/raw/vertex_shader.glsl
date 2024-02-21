attribute vec3 idVertice;

void main() {
    gl_Position = vec4(idVertice, 1.0);
}