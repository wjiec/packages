//
//  Badge.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/15.
//

import SwiftUI

struct Badge: View {
    var badgeSymbols: some View {
        ForEach(0..<8) { index in
            RotatedBadgeSymbol(
                angle: .degrees((Double(index) / 8.0) * 360)
            )
        }
        .opacity(0.5)
    }
    
    var body: some View {
        ZStack {
            BadgeBackground()
            
            GeometryReader { geometry in
                badgeSymbols
                    .scaleEffect(0.35, anchor: .center)
            }
            .scaledToFit()
        }
        .scaledToFit()
    }
}

#Preview {
    Badge()
}
