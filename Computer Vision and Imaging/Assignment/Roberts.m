function output = read(filename)

% Read the image and convert it to double precision
color_shakey = im2double(imread(filename));
% Convert the image to greyscale using the standard NTSC conversion formula
% intensity = 0.2989*red + 0.5870*green + 0.1140*blue
shakey = 0.2989*color_shakey(:,:,1) + 0.5870*color_shakey(:,:,2) + 0.1140*color_shakey(:,:,3);

% Making the Gaussian filter
% gaussian_filter_1D is the Gaussian filter in 1D form
% gaussian_filter is the Gaussian filter in 2D form. It's the product of
% the 1D Gaussian filter and its transpose.
s = 2; % Standard deviation
kernel = 5; % Size of the kernel
x = -floor(kernel/2):floor(kernel/2)
gaussian_filter_1D = 1/(s*sqrt(2*pi)) * exp(-(x).^2/(2*(s).^2));
gaussian_filter_1D = gaussian_filter_1D/sum(gaussian_filter_1D);
gaussian_filter = gaussian_filter_1D'*gaussian_filter_1D;

% Applying the Gaussian filter onto the image
shakey_gaussian = conv2(shakey,gaussian_filter,'valid');

% Kernels for Roberts
robertsX = [1, 0; 0, -1];
robertsY = [0, 1; -1, 0];

% Apply the kernels
shakey_robertsX = conv2(shakey_gaussian, robertsX,"valid");
shakey_robertsY = conv2(shakey_gaussian,robertsY,"valid");

% Find the magnitude
output1 = magnitude(shakey_robertsX, shakey_robertsY);

% The resulting image is too dark; this will brighten it up by intensifying
% the pixels
%output1 = output1*3;

% Threshold the output image
% output1(output1 <= 0.12) = 0;

% Transform the output image into its negative for better display
max_val = max(output1(:));
output = max_val-output1;

% Display the output image
imshow(output);
