function output = function2(g,e)

% Read the groundtruth image 
% Read the original image and apply the edge detectors
groundTruth = imread(g);
sobel_edge = Sobel(e);
roberts_edge = Roberts(e);
fog_edge = FirstOrderGaussian(e);
laplacian_edge = Laplacian(e);
log_edge = LoG2(e);

% True Positive Rate and False Positive Rate Arrays for each edge detector
TPR_Sobel = [];
FPR_Sobel = [];

TPR_Roberts = [];
FPR_Roberts = [];

TPR_FoG = [];
FPR_FoG = [];

TPR_Laplacian = [];
FPR_Laplacian = [];

TPR_LoG = [];
FPR_LoG = [];

[TPR_Sobel, FPR_Sobel] = TPR_FPR(groundTruth,sobel_edge);
[TPR_Roberts, FPR_Roberts] = TPR_FPR(groundTruth,roberts_edge);
[TPR_FoG, FPR_FoG] = TPR_FPR(groundTruth,fog_edge);
[TPR_Laplacian, FPR_Laplacian] = TPR_FPR(groundTruth,laplacian_edge);
[TPR_LoG, FPR_LoG] = TPR_FPR(groundTruth,log_edge);

% Displaying the ROC curve
figure; hold on
a1 = plot(TPR_Roberts,FPR_Roberts);
M1 = "Roberts' ROC curve";
a2 = plot(TPR_Sobel,FPR_Sobel);
M2 = "Sobel's ROC curve";
a3 = plot(TPR_FoG,FPR_FoG);
M3 = "First Order Gaussian's ROC curve";
a4 = plot(TPR_Laplacian,FPR_Laplacian);
M4 = "Laplacian's ROC curve";
a5 = plot(TPR_LoG,FPR_LoG);
M5 = "Laplacian of Gaussian's ROC curve";
xlabel("False Positive Rate");
ylabel("True Positive Rate");
title("ROC analysis");
legend([a1,a2,a3,a4,a5], [M1, M2,M3,M4,M5]);
legend('Location','eastoutside');




