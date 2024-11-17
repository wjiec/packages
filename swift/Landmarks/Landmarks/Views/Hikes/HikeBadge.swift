//
//  HikeBadge.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct HikeBadge: View {
    var name: String
    
    var body: some View {
        VStack {
            // The badge’s drawing logic produces a result that depends on the
            // size of the frame in which it renders. To ensure the desired
            // appearance, render in a frame of 300 x 300 points. To get the
            // desired size for the final graphic, then scale the rendered result
            // and place it in a comparably smaller frame.
            Badge()
                // because the RotatedBadgeSymbol has -150 padding
                .frame(width: 300, height: 300)
                .scaleEffect(1.0 / 3.0)
                .frame(width: 100, height: 100)
            
            Text(name)
                .font(.caption)
                .accessibilityLabel("Badge for \(name)")
        }
    }
}

#Preview {
    HikeBadge(name: "Badge Name")
}
