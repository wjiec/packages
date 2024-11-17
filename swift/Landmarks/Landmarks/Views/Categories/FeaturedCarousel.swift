//
//  FeaturedCarousel.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct FeaturedCarousel: View {
    var features: [Landmark]
    
    var body: some View {
        CarouselView(features, autoScroll: .constant(true)) { landmark in
            ZStack {
                landmark.featureImage?
                    .resizable()
                    .clipped()
                    .scaledToFill()
                    .overlay {
                        TextOverlay(title: landmark.name, subtitle: landmark.park)
                    }
            }
            .scaledToFit()
        }
    }
}

#Preview {
    FeaturedCarousel(features: ModelData().features)
}
