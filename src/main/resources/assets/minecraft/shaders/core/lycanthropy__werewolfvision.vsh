#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

out float vertexDistance;
out vec4 vertexColor;
out vec4 lightMapColor;
out vec4 overlayColor;
out vec2 texCoord0;
out vec4 normal;

uniform float GameTime;



float wobble(vec2 pair) {
    return (fract(sin(dot(pair, vec2(10,100))/2)) - 0.5);
}

void main() {
    float salt = wobble(vec2(GameTime, GameTime));
    vec3 offset = vec3(wobble(salt * Position.yz), wobble(salt * Position.xz), wobble(salt * Position.xy))/40;
    gl_Position = ProjMat * ModelViewMat * vec4(Position + offset, 1.0);

    vertexDistance = length((ModelViewMat * vec4(Position + offset, 1.0)).xyz);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
    lightMapColor = texelFetch(Sampler2, UV2 / 16, 0);
    overlayColor = texelFetch(Sampler1, UV1, 0);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}