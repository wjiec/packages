//
//  GraphCapsule.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/15.
//

import SwiftUI

struct GraphCapsule: View, Equatable {
    let color: Color
    let overall: Range<Double>
    let range: Range<Double>
    let height: CGFloat
    
    var heightRatio: CGFloat {
        range.magnitude() / overall.magnitude()
    }
    
    var offsetRatio: CGFloat {
        -(range.lowerBound - overall.lowerBound) / overall.magnitude()
    }
    
    var body: some View {
        GeometryReader { geometry in
            let h = max(height * heightRatio, geometry.size.width)
            
            return Capsule()
                .fill(color)
                .frame(height: h)
                .offset(y: height - h + offsetRatio * height)
        }
    }
}

#Preview {
    HStack(alignment: .bottom) {
        GraphCapsule(
            color: .cyan,
            overall: 0..<10,
            range: 1.3..<3.1,
            height: 300
        )
        
        GraphCapsule(
            color: .cyan,
            overall: 0..<10,
            range: 0.5..<1.5,
            height: 300
        )
        
        GraphCapsule(
            color: .cyan,
            overall: 0..<10,
            range: 0..<8,
            height: 300
        )
        
        GraphCapsule(
            color: .cyan,
            overall: 0..<10,
            range: 3..<7,
            height: 300
        )
        
        GraphCapsule(
            color: .cyan,
            overall: 0..<10,
            range: 0.5..<6.7,
            height: 300
        )
    }
    .frame(height: 300)
}
