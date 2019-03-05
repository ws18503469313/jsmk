package com.itmuch.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.itmuch.dto.NoteDTO;

public class SerialaizerUtils/* <T>*/ {
	private static Schema<NoteDTO> schema = RuntimeSchema.createFrom(NoteDTO.class);
	
	public static byte[] serialaizer(NoteDTO note) {
		final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			return serialaizeInternal(note, schema, buffer);
		} catch (final Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}finally {
			buffer.clear();
		}
	}
	
	public static NoteDTO deserialaize(final byte[] bytes) {
		try {
			NoteDTO note = deserialaizeInternal(bytes, schema.newMessage(), schema);
			if(note != null) {
				return note;
			}
		} catch (final Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return null;
	}
	/*protected Class<T> clz;

	public SerialaizerUtils() { 
		this.clz = ((Class)((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}*/

//	public  final Schema<T> schema = RuntimeSchema.createFrom(clz);
//	public <T> byte[] serialaizer(T t) {
//		final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//		try {
//			return serialaizeInternal(t, schema, buffer);
//		} catch (final Exception e) {
//			throw new IllegalStateException(e.getMessage(), e);
//		}finally {
//			buffer.clear();
//		}
//	}
	/**
	 *  序列化
	 * @param bytes
	 * @return
	 */
	private static <T>byte[] serialaizeInternal(final T source, final Schema<T> schema, final LinkedBuffer buffer){
		
		return ProtostuffIOUtil.toByteArray(source, schema, buffer);
	}
//	private static <T>byte[] serialaizeInternal(final T source, final Schema<T> schema, final LinkedBuffer buffer){
//		
//		return ProtostuffIOUtil.toByteArray(source, schema, buffer);
//	}
	/**
	 * 反 序列化
	 * @param source
	 * @param schema
	 * @param buffer
	 * @return
	 */
	public static <T> T deserialaizeInternal(final byte[] bytes, final T result, final Schema<T> schema) {
		ProtostuffIOUtil.mergeFrom(bytes, result, schema);
		return result;
	}
}
