function output = read(filename)

% Read the image and convert it to double precision
color_shakey = im2double(imread(filename));
% Convert the image to greyscale using the standard NTSC conversion formula
% intensity = 0.2989*red+0.5870*green+0.1140*blue
shakey = 0.2989*color_shakey(:,:,1)+0.5870*color_shakey(:,:,2)+0.1140*color_shakey(:,:,3);

% Making the Gaussian kernel
% gaussian_filter_1D is the Gaussian kernel in 1D form
s = 2; % Standard deviation
kernel = 5; % Size of the kernel
x = -floor(kernel/2):floor(kernel/2)
gaussian_filter_1D = 1/(s*sqrt(2*pi)) * exp(-(x).^2/(2*(s).^2));

% Making the 1st derivative Gaussian kernel
gaussian_filter_first_order = gradient(gaussian_filter_1D);

% Making the 1st derivative Gaussian kernels in x and y direction
gaussian_filter_first_order_x = conv2(shakey, gaussian_filter_first_order, 'valid');
gaussian_filter_first_order_y = conv2(shakey, gaussian_filter_first_order, 'valid');

% Compute the magnitude
mag = magnitude(gaussian_filter_first_order_x, gaussian_filter_first_order_y);

% Transform the output image into its negative for better display
max_val = max(mag(:));
output = max_val-mag;

% Threshold the output image
output(output <= 0.03) = 0;

% Show resulting image
imshow(output, []);
