import coco_proc, trainer


if __name__ == '__main__':

    z, zd, zt = coco_proc.process(context=5)
    trainer.trainer(z, zd)
