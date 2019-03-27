package com.shliama.augmentedvideotutorial

import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.shliama.augmentedvideotutorial.VideoScaleType.*

class VideoAnchorNode : AnchorNode() {

    fun setVideoProperties(
        videoWidth: Float, videoHeight: Float, videoRotation: Float,
        imageWidth: Float, imageHeight: Float,
        videoScaleType: VideoScaleType
    ) {
        scaleNode(videoWidth, videoHeight, imageWidth, imageHeight, videoScaleType)
        rotateNode(videoRotation)
    }

    private fun scaleNode(
        videoWidth: Float, videoHeight: Float,
        imageWidth: Float, imageHeight: Float,
        videoScaleType: VideoScaleType
    ) {
        when (videoScaleType) {
            FitXY -> scaleFitXY(imageWidth, imageHeight)
            CenterCrop -> scaleCenterCrop(videoWidth, videoHeight, imageWidth, imageHeight)
            CenterInside -> scaleCenterInside(videoWidth, videoHeight, imageWidth, imageHeight)
        }
    }

    private fun rotateNode(videoRotation: Float) {
        localRotation = Quaternion.axisAngle(Vector3(0.0f, -1.0f, 0.0f), videoRotation)
    }

    private fun scaleFitXY(imageWidth: Float, imageHeight: Float) {
        localScale = Vector3(imageWidth, 1.0f, imageHeight)
    }

    private fun scaleCenterCrop(videoWidth: Float, videoHeight: Float, imageWidth: Float, imageHeight: Float) {
        val isVideoVertical = videoHeight > videoWidth
        val videoAspectRatio = if (isVideoVertical) videoHeight / videoWidth else videoWidth / videoHeight
        val imageAspectRatio = if (isVideoVertical) imageHeight / imageWidth else imageWidth / imageHeight

        localScale = if (isVideoVertical) {
            if (videoAspectRatio > imageAspectRatio) {
                Vector3(imageWidth, 1.0f, imageWidth * videoAspectRatio)
            } else {
                Vector3(imageHeight / videoAspectRatio, 1.0f, imageHeight)
            }
        } else {
            if (videoAspectRatio > imageAspectRatio) {
                Vector3(imageHeight * videoAspectRatio, 1.0f, imageHeight)
            } else {
                Vector3(imageWidth, 1.0f, imageWidth / videoAspectRatio)
            }
        }
    }

    private fun scaleCenterInside(videoWidth: Float, videoHeight: Float, imageWidth: Float, imageHeight: Float) {
        val isVideoVertical = videoHeight > videoWidth
        val videoAspectRatio = if (isVideoVertical) videoHeight / videoWidth else videoWidth / videoHeight
        val imageAspectRatio = if (isVideoVertical) imageHeight / imageWidth else imageWidth / imageHeight

        localScale = if (isVideoVertical) {
            if (videoAspectRatio < imageAspectRatio) {
                Vector3(imageWidth, 1.0f, imageWidth * videoAspectRatio)
            } else {
                Vector3(imageHeight / videoAspectRatio, 1.0f, imageHeight)
            }
        } else {
            if (videoAspectRatio < imageAspectRatio) {
                Vector3(imageHeight * videoAspectRatio, 1.0f, imageHeight)
            } else {
                Vector3(imageWidth, 1.0f, imageWidth / videoAspectRatio)
            }
        }
    }
}