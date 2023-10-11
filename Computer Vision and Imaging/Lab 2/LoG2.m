function output = read(filename)

% Read the image and convert it to double precision
color_shakey = im2double(imread(filename));
% Convert the image to greyscale using the standard NTSC conversion formula
% intensity = 0.2989*red + 0.5870*green + 0.1140*blue
shakey = 0.2989*color_shakey(:,:,1) + 0.5870*color_shakey(:,:,2) + 0.1140*color_shakey(:,:,3);

% Making the LoG kernel by using its formula
s = 1; % Standard deviation
kernel = 5; % Size of the kernel
x = -floor(kernel/2):floor(kernel/2);
y = x';
LoG_filter = (-1/(pi*(s).^4)) * (1-((x).^2+(y).^2)/2*(s).^2) * exp(-((x).^2+(y).^2)/2*(s).^2);
LoG_filter = LoG_filter/sum(LoG_filter); % Normalizing the LoG filter

% Applying the kernel onto the image
output1 = conv2(shakey, LoG_filter,'valid');

% Select only the zero crossings as edge points
%output1 = edge(shakey_filtered, 'zerocross');

% Transform the output image into its negative for better display
max_val = max(output1(:));
output = max_val-output1;

% Threshold the output image
%output(output <= 0.006) = 0;

% Show the resulting image
imshow(output);