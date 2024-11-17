//
//  CircleImage.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

struct CircleImage: View {
    var image: Image
    
    var body: some View {
        image
            .clipShape(.circle)
            .overlay {
                Circle()
                    .stroke(lineWidth: 4)
                    .foregroundStyle(.white)
            }
            .shadow(radius: 7)
    }
}

#Preview {
    CircleImage(image: Image("turtlerock"))
}
