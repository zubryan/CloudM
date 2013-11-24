package com.github.cloudm.invoke.init;



/**
 * 
 * @author andy.wan
 *
 */
public class GeneratorLocalTemplateFile {

	/*private static final Logger LOGGER = Logger.getLogger(GeneratorLocalTemplateFile.class);
	
	@Autowired
	private PPiaasCallbackTemplateRepository templateService;
	
	@Value("${default.win}")
    private String defaultWinRootPath ;

	@Value("${default.linux}")
	private String defaultLinuxRootPath ;
	
	*//**
	 * 生成模版文件
	 *//*
	public void init() {
		LOGGER.info("begin generator template file...");
		boolean delFullTemplates=this.deleteTemplateFile("*.vm");
		if(delFullTemplates){
			Collection<PPiaasCallbackTemplate> templates=templateService.findAll();
			if(templates!=null){
				for (PPiaasCallbackTemplate pPiaasCallbackTemplate : templates) {
					String templateBody=pPiaasCallbackTemplate.getTemplate();
					Integer method=pPiaasCallbackTemplate.getMethod();
					//如果方法为post或�?put方法
					if(this.isPostOrPut(method)){
						if(StringUtils.isNotBlank(templateBody)){
							String genteratorFilePath="";
							//根据不同的操作系统构建对应的根目�?
							if (SystemPropertyUtils.isWindowsOS()) {
								genteratorFilePath=defaultWinRootPath;
							} else if (SystemPropertyUtils.isLinuxOS()) {
								genteratorFilePath=defaultLinuxRootPath;
							}
							//�?��生成模版文件到对应的目录�?
							String newPath=genteratorFilePath+pPiaasCallbackTemplate.getName()+".vm";
							boolean flag = FileUtils.createFile(newPath);
							LOGGER.debug("[ Generator template file flag is ]:"+flag);
							if (flag) {
								File file=new File(newPath);
								FileOutputStream output =null;
								try {
									output = new FileOutputStream(file);
									IOUtils.write(templateBody, output);
								} catch (ResourceNotFoundException e) {
									throw new ResourceNotFoundException(ModuleCode.PIAAS,"init template resource exception!");
								} catch (IOException e) {
									throw new InputOutputException(ModuleCode.PIAAS,"init template resource exception!");
								}finally{
									IOUtils.closeQuietly(output);
								}
							}
						}else{
							throw new IllegalArgsException(ModuleCode.PIAAS,"template content is null");
						}
					}else{
						continue;
					}
				}
				LOGGER.debug("generator local template file is ok!");
			}else{
				throw new IllegalArgsException(ModuleCode.PIAAS,"query template is null");
			}
		}else{
			throw new PiaasDeleteFileException("delete local template error");
		}
		
	}
	
	*//**
	 * 删除模版文件
	 * 
	 * @param pattern 通配�?
	 * @return
	 *//*
	private boolean deleteTemplateFile(String pattern){
		boolean delFlag=false;
		String deletePath="";
		if (SystemPropertyUtils.isWindowsOS()) {
			deletePath=defaultWinRootPath;
		} else if (SystemPropertyUtils.isLinuxOS()) {
			deletePath=defaultLinuxRootPath;
		}
		delFlag=FileUtils.delete(deletePath, pattern);
		return delFlag;
	}
	*/
	/**
	 * 判断请求方法是否为post或�?put
	 * @param i
	 * @return
	 */
	private boolean isPostOrPut(int i){
        return (i & 1) != 0;
	}
}
