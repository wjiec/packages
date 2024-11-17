//
//  LandmarkRow.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

struct LandmarkRow: View {
    var landmark: Landmark
    
    var body: some View {
        HStack {
            landmark.image
                .resizable()
                .frame(width: 50, height: 50)

            Text(landmark.name)

            Spacer()
            
            // In SwiftUI blocks, you use if statements to conditionally include views.
            if landmark.isFavorite {
                Image(systemName: "star.fill")
                    .foregroundStyle(.yellow)
            }
        }
    }
}

// By default, the canvas labels previews using the line number they appear on.
#Preview("Turtle Rock") {
    LandmarkRow(landmark: ModelData().landmarks[0])
}

// Add a second preview macro that uses the the second element in the landmarks array.
//
// Adding previews enables you to see how your views behave with different data.
#Preview("Salmon") {
    LandmarkRow(landmark: ModelData().landmarks[1])
}

// When you want to preview different versions of a view side by side, you
// can instead group them together in a single collection view.
#Preview("Groups") {
    let landmarks = ModelData().landmarks
    // Group is a container for grouping view content. Xcode renders the
    // group’s child views stacked as one preview in the canvas.
    Group {
        LandmarkRow(landmark: landmarks[0])
        LandmarkRow(landmark: landmarks[1])
    }
}
