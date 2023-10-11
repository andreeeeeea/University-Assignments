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
MH_filter = (1/(s).^2) * ((((x).^2+(y).^2)/(s).^2)-2) * exp(-((x).^2+(y).^2)/2*(s).^2);
MH_filter = MH_filter/sum(MH_filter); % Normalizing the MH filter

% Applying the kernel onto the image
shakey_filtered = conv2(shakey, MH_filter,'valid');

% Select only the zero crossings as edge points
output1 = edge(shakey_filtered, 'zerocross');

max_val = max(output1(:));
output = max_val-output1;

% Show the resulting image
imshow(output);