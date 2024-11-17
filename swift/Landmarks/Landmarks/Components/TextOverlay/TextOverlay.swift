//
//  TextOverlay.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct TextOverlay: View {
    var title: String
    var subtitle: String
    
    private var gradient: LinearGradient {
        .linearGradient(
            Gradient(colors: [.black.opacity(0.6), .black.opacity(0)]),
            startPoint: .bottom,
            endPoint: .center
        )
    }
    
    var body: some View {
        ZStack(alignment: .bottomLeading) {
            gradient
            
            VStack(alignment: .leading) {
                Text(title)
                    .font(.title)
                    .bold()
                Text(subtitle)
            }
            .padding()
        }
        .foregroundStyle(.white)
    }
}

#Preview {
    TextOverlay(title: "Foobar", subtitle: "Some subtitle")
}
