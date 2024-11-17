//
//  RotatedBadgeSymbol.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/14.
//

import SwiftUI

struct RotatedBadgeSymbol: View {
    let angle: Angle
    
    var body: some View {
        BadgeSymbol()
            .padding(.top, -150)
            .rotationEffect(angle)
    }
}

#Preview {
    RotatedBadgeSymbol(angle: Angle(degrees: 0))
}
