#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_brightness;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords) * v_color;
    gl_FragColor = vec4(color.rgb * u_brightness, color.a);
}