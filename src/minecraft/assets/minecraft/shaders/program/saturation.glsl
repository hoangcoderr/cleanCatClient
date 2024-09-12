#version 120

uniform sampler2D texture;
uniform float saturation;

void main() {
    vec4 color = texture2D(texture, gl_TexCoord[0].st);
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec3 result = mix(vec3(gray), color.rgb, saturation);
    gl_FragColor = vec4(result, color.a);
}