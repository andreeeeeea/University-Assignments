function output = read(filename)

% Read the image and convert it to double precision
shakey = im2double(imread(filename));

% Laplacian filter
laplacian = [0 1 0; 1 -4 1; 0 1 0]; 


% Making the Gaussian kernel
% gaussian_filter_1D is the Gaussian filter in 1D form
% gaussian_filter is the Gaussian filter in 2D form. It's the product of
% the 1D Gaussian filter and its transpose.
s = 2; % Standard deviation
kernel = 10; % Size of the kernel
x = -floor(kernel/2):floor(kernel/2);
gaussian_filter_1D = 1/(s*sqrt(2*pi)) * exp(-(x).^2/(2*(s).^2));
gaussian_filter_1D = gaussian_filter_1D/sum(gaussian_filter_1D); % Normalizing the 1D filter
gaussian_filter = gaussian_filter_1D'*gaussian_filter_1D;

% Apply Gaussian kernel to the image
shakey_gaussian = conv2(shakey,gaussian_filter,'valid');

% Apply Laplacian to the result 
shakey_LoG = conv2(shakey_gaussian, laplacian, 'valid');

% Select only the zero crossings as edge points
output = edge(shakey_LoG, 'zerocross');

% Show the resulting image
imshow(output);
