function [TPR_array, FPR_array] = TPR_FPR(groundTruth, edgeDetection)

TP = 0; FP = 0; TN = 0; FN = 0;

threshold = [0:0.01:1]

for i = 1:length(threshold)
    fprintf('i is: %d\n', i);
    disp(threshold(i));
    TP = 0; FP = 0; TN = 0; FN = 0;
    edgeDetection = imresize(edgeDetection, size(groundTruth));
    fprintf("Size of edgeDetection: %d\n", size(edgeDetection));

    edgeDetection_thresholded = edgeDetection > threshold(i);
    groundTruth_thresholded = groundTruth > threshold(i);
    groundTruth_thresholded = ~groundTruth_thresholded;


    groundTruth_mask = logical(groundTruth_thresholded);
    edgeDetection_mask = logical(edgeDetection_thresholded);


    TP = sum(groundTruth_mask & edgeDetection_mask, 'all');
    FP = sum(~groundTruth_mask & edgeDetection_mask, 'all');
    TN = sum(~groundTruth_mask & ~edgeDetection_mask, 'all');
    FN = sum(groundTruth_mask & ~edgeDetection_mask, 'all');

    %fprintf('TP: %d\n', TP);
    %fprintf('FN: %d\n', FP);
    %fprintf('TP: %d\n', FN);
    %fprintf('FN: %d\n', TN);

    TPR = TP/(TP+FN);
    FPR = FP/(FP+TN);

    TPR_array(i) = TPR;
    FPR_array(i) = FPR;
end