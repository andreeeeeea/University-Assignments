function output = read(filename)

% Read the image and convert it to double precision
color_shakey = im2double(imread(filename));
% Convert the image to greyscale using the standard NTSC conversion formula
% intensity = 0.2989*red + 0.5870*green + 0.1140*blue
shakey = 0.2989*color_shakey(:,:,1) + 0.5870*color_shakey(:,:,2) + 0.1140*color_shakey(:,:,3);

% Mean filtering the image to reduce noise
%shakey_filtered = conv2(shakey, ones(5)/25, 'same');

% Laplacian filter
laplacian = [0 1 0; 1 -4 1; 0 1 0]; 

% Applying Laplacian onto the image
output1 = conv2(shakey, laplacian, 'valid');

% Zero-cross edge extraction
output = edge(shakey_laplacian,'zerocross'); 

% Transform the output image into its negative for better display
max_val = max(shakey_laplacian(:));
output = max_val-shakey_laplacian;

% Threshold the output image
%output(output <= 0.05) = 0;
max_val = max(output1(:));
output = max_val-output1;
imshow(output);