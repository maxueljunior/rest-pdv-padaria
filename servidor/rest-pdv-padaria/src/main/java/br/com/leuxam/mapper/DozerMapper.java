package br.com.leuxam.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;

import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.model.Cliente;


public class DozerMapper {
	
	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}
	
	/*
	private static Mapper mapperConfig = null;
	
	public static ClienteVO toValueObject(Cliente cliente, Class<ClienteVO> class1) {
		BeanMappingBuilder builder = new BeanMappingBuilder() {
			
			@Override
			protected void configure() {
				mapping(Cliente.class, ClienteVO.class, TypeMappingOptions.oneWay())
				.fields(field("id").accessible(true), field("key"));
			}
		};
		
		mapperConfig = DozerBeanMapperBuilder.create()
				.withMappingBuilder(builder)
				.build();
		return mapperConfig.map(cliente, class1);
	}
	*/
}
