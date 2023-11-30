package dev.lounres.cuttingEdge.serialization

import dev.lounres.kone.misc.lattices.Position
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


class PositionSerializer<C, K>: KSerializer<Position<C, K>> {
    override val descriptor: SerialDescriptor = TODO("Not yet implemented")

    override fun deserialize(decoder: Decoder): Position<C, K> {
        TODO("Not yet implemented")
    }

    override fun serialize(encoder: Encoder, value: Position<C, K>) {
        TODO("Not yet implemented")
    }

}