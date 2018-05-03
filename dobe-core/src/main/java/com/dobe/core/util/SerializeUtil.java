package com.dobe.core.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Described	: 序列化、反序列化工具类
 * @project		: com.sirding.core.utils.SerializeUtil
 * @author 		: zc.ding
 * @date 		: 2016年11月29日
 */
public class SerializeUtil {

	private static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

	//**********************************************************
	//				JDK原生序列化、反序列化操作
	//**********************************************************
	/**
	 * 
	 * @Described			: java原生对对象进行序列化操作;注：obj一定要实现Serializable接口
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param obj
	 * @return
	 */
	public static byte[] serialize(Object obj) {
		try (
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);
				){
			os.writeObject(obj);
			return bos.toByteArray();
		} catch (Exception e) {
			logger.error("serialize error", e);
		}
		return new byte[]{};
	}

	/**
	 * @Described			: java原生对对象的反序列化;注：obj一定要实现Serializable接口
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param buf
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unSerialize(byte[] buf) {
		if(buf == null){
			return null;
		}
		T result = null;
		try (
				ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(buf));
				){
			if (buf != null) {
				result = (T) is.readObject();
			}
		} catch (Exception e) {
			logger.error("deserialize error", e);
		}
		return result;
	}
	//================== JDK原生 ===================== END =======================

	
	//**********************************************************
	//				FST序列化、反序列化操作
	//**********************************************************
	private static FSTConfiguration FST = FSTConfiguration.createDefaultConfiguration();
	
	/**
	 * @Described			: FST进行对象的序列化操作;注：obj一定要实现Serializable接口
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param obj
	 * @return
	 */
	public static byte[] serializeFst(Object obj){
		return FST.asByteArray(obj);
	}
	
	/**
	 * @Described			: FST进行对象反序列化操作;注：obj一定要实现Serializable接口
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param buf
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unSerializeFst(byte[] buf){
		if(buf == null){
			return null;
		}
		T result = null;
		try {
			result = (T) FST.asObject(buf);
		} catch (Exception e) {
			logger.error("FST方式反序列化失败", e);
		}
		return result;
	}
	//======================= FST ============== END ======================================
	
	
	
	//**********************************************************
	//				Kryo序列化、反序列化操作
	//**********************************************************
	/**
	 * @Described			: Kryo进行对象序列化操作
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param obj
	 * @return
	 */
	public static byte[] serializeKryo(Object obj) {  
        Kryo kryo = new Kryo();  
        byte[] buffer = new byte[20480];		//20K
        try(
        		Output output = new Output(buffer);
        		)
        {
        	kryo.writeClassAndObject(output, obj);  
        	return output.toBytes();  
        	
        }catch (Exception e) {
        	logger.error("Kryo方式序列化操作失败", e);
        }
        return buffer;  
    }  
  
	/**
	 * @Described			: Kryo进行对象反序列化操作，对象不需要实现序列化
	 * @author				: zc.ding
	 * @date 				: 2016年11月29日
	 * @param buf
	 * @param clazz
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public static <T> T unSerializeKryo(byte[] buf) {
    	if(buf == null){
    		return null;
    	}
    	Kryo kryo = new Kryo();
    	T result = null;
        try(  
        		Input input = new Input(buf);  
        		) {
        	result = (T) kryo.readClassAndObject(input);
        }catch (Exception e) {  
        	logger.error("Kryo方式反序列化失败", e);
        }  
        return result;  
    }  
    //======================= Kryo ============== END ======================================

}
