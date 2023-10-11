function [TPR_array, FPR_array] = TPR_FPR(groundTruth, edgeDetection)

% Initialize the True Positive, False Positive, True Negative and False
% Negative
TP = 0; FP = 0; TN = 0; FN = 0;

% Array for the thresholds used, from 0 to 1 increasing by 0.01 each
% iteration
threshold = [0:0.01:1]

for i = 1:length(threshold)

    % Reset the TP, FP, TN, FN each iterations
    TP = 0; FP = 0; TN = 0; FN = 0;
    % Resive the image with the edge detection applied onto it so it is the
    % same size as the ground truth image
    edgeDetection = imresize(edgeDetection, size(groundTruth));

    % Apply the thresholds
    % The resulting ground truth image needs to be switched to its negative
    edgeDetection_thresholded = edgeDetection > threshold(i);
    groundTruth_thresholded = groundTruth > threshold(i);
    groundTruth_thresholded = ~groundTruth_thresholded;
    
    % Logical masks
    groundTruth_mask = logical(groundTruth_thresholded);
    edgeDetection_mask = logical(edgeDetection_thresholded);

    % Computing the TP, FP, TN, FN
    TP = sum(groundTruth_mask & edgeDetection_mask, 'all');
    FP = sum(~groundTruth_mask & edgeDetection_mask, 'all');
    TN = sum(~groundTruth_mask & ~edgeDetection_mask, 'all');
    FN = sum(groundTruth_mask & ~edgeDetection_mask, 'all');

    % Computing the True Positive Rate and False Positive Rate
    TPR = TP/(TP+FN);
    FPR = FP/(FP+TN);

    % Put them into their respective arrays
    TPR_array(i) = TPR;
    FPR_array(i) = FPR;
end
