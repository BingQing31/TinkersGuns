#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0; // GrayScale / FullTex
uniform sampler2D Sampler2; // LightMap

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

uniform vec2 ScreenSize;
uniform vec2 GuiScreenSize;

in float vertexDistance;
in vec4 vertex_color;
in vec4 light_map_color;
in vec2 texCoord0;
in vec2 texCoord1;
in vec2 texCoord2;
in vec4 normal;

out vec4 fragColor;

void main() {

    vec2 pos = gl_FragCoord.xy / ScreenSize;
    pos -= vec2(0.5);
    vec2 guiPos = pos * GuiScreenSize;

    vec2 uv0 = mod((guiPos + 8.0) / 16.0, 1.0);

    if(guiPos.x < -8.0 || guiPos.x > 8.0 || guiPos.y < -8.0 || guiPos.y > 8.0) {
        discard;
    }

    vec2 atlasSize = textureSize(Sampler0, 0);
    vec2 uv = floor(texCoord0 * atlasSize / 16.0) + uv0;
    uv = uv * 16.0 / atlasSize;
    vec4 color = texture(Sampler0, uv);

    if (color.a < 0.1) {
        discard;
    }

    //color *= vertex_color * ColorModulator;
    fragColor = color;
}

