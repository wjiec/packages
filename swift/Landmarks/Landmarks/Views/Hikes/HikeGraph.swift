//
//  HikeGraph.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/15.
//

import SwiftUI

struct HikeGraph: View {
    var hike: Hike
    var path: KeyPath<Hike.Observation, Range<Double>>
    
    var color: Color {
        switch (path) {
        case \.elevation: .blue
        case \.heartRate: .red
        case \.pace: .orange
        default: .black
        }
    }
    
    var ranges: [Range<Double>] {
        hike.observations.map({ $0[keyPath: path] })
    }
    
    var overall: Range<Double> {
        guard !ranges.isEmpty else { return 0..<0 }

        let low = ranges.lazy.map { $0.lowerBound }.min()!
        let high = ranges.lazy.map { $0.upperBound }.max()!

        return low..<high
    }

    var body: some View {
        GeometryReader { geometry in
            HStack(spacing: geometry.size.width / 120) {
                ForEach(Array(ranges.enumerated()), id: \.offset) { index, range in
                    GraphCapsule(color: color, overall: overall, range: range, height: geometry.size.height)
                        .animation(.ripple(index: index))
                }
            }
        }
    }
}

#Preview {
    let hike = ModelData().hikes[0]
    
    Group {
        HikeGraph(hike: hike, path: \.elevation)
        HikeGraph(hike: hike, path: \.heartRate)
        HikeGraph(hike: hike, path: \.pace)
            .frame(width: 50, height: 30)
    }
}
